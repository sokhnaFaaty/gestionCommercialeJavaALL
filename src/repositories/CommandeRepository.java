package repositories;

import config.DatabaseConfig;
import entities.Client;
import entities.Commande;
import entities.LigneCommande;
import entities.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsable des opérations SQL liées aux commandes.
 * Gère deux tables :
 *  - "commande"       : les informations générales d'une commande (numéro, date, montant, statut, client)
 *  - "ligne_commande"  : le détail des produits achetés dans une commande (produit + quantité)
 *
 * Utilise DatabaseConfig pour obtenir une connexion à chaque appel de méthode,
 * plutôt qu'une connexion unique partagée (Singleton) comme le faisait ConnexionBD.
 * Cela évite de garder une connexion ouverte trop longtemps et rend le code
 * plus sûr en cas d'erreur (la connexion est toujours fermée grâce au try-with-resources).
 */
public class CommandeRepository {

    // Fournit une nouvelle connexion à la base de données à chaque appel de getConnection()
    private DatabaseConfig dbConfig = new DatabaseConfig();

    // Repository utilisé pour récupérer les infos complètes d'un produit dans une ligne de commande
    private ProduitRepository produitRepository = new ProduitRepository();

    // Repository utilisé pour récupérer les infos complètes du client associé à une commande
    private ClientRepository clientRepository = new ClientRepository();

    /**
     * Insère une nouvelle commande dans la base de données.
     * Utilise RETURN_GENERATED_KEYS pour récupérer l'ID auto-généré par la base
     * et le réinjecter dans l'objet Commande passé en paramètre.
     *
     * @param commande la commande à insérer (sans ID, il sera généré par la BD)
     * @return la même commande, mais avec son ID désormais renseigné
     */
    public Commande ajouter(Commande commande) {
        String sql = "INSERT INTO commande (numero, date_commande, montant_total, validee, client_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        // try-with-resources : Connection et PreparedStatement seront fermés automatiquement,
        // même en cas d'exception, ce qui évite les fuites de connexions.
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // On lie chaque paramètre "?" du SQL à une valeur de l'objet commande
            ps.setString(1, commande.getNumero());
            ps.setDate(2, Date.valueOf(commande.getDate())); // conversion LocalDate -> java.sql.Date
            ps.setDouble(3, commande.getMontantTotal());
            ps.setBoolean(4, commande.isValidee());
            ps.setInt(5, commande.getClient().getId());

            ps.executeUpdate(); // exécute l'INSERT

            // Récupère l'ID généré automatiquement par la base (clé primaire auto-incrémentée)
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    commande.setId(cles.getInt(1));
                }
            }
            return commande;

        } catch (SQLException e) {
            // On transforme l'exception vérifiée SQLException en exception non-vérifiée
            // pour ne pas obliger tous les appelants à la gérer explicitement.
            throw new RuntimeException("Erreur lors de l'ajout de la commande", e);
        }
    }

    /**
     * Ajoute une ligne de commande (un produit + une quantité) à une commande existante,
     * puis recalcule et sauvegarde le nouveau montant total de la commande.
     *
     * @param commande la commande à laquelle on ajoute une ligne (doit déjà avoir un ID)
     * @param ligne    la ligne à insérer (produit + quantité)
     */
    public void ajouterLigne(Commande commande, LigneCommande ligne) {
        String sql = "INSERT INTO ligne_commande (commande_id, produit_id, quantite) VALUES (?, ?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getId());
            ps.setInt(2, ligne.getProduit().getId());
            ps.setInt(3, ligne.getQuantite());
            ps.executeUpdate();

            // Après ajout d'une ligne, le montant total de la commande change :
            // on met donc à jour la colonne montant_total en base pour rester cohérent.
            mettreAJourMontantTotal(commande);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la ligne de commande", e);
        }
    }

    /**
     * Méthode privée utilitaire : met à jour uniquement le montant total d'une commande
     * en base de données. Appelée automatiquement après l'ajout d'une ligne.
     *
     * @param commande la commande dont le montant total doit être synchronisé en base
     */
    private void mettreAJourMontantTotal(Commande commande) {
        String sql = "UPDATE commande SET montant_total = ? WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // On suppose que commande.getMontantTotal() recalcule dynamiquement
            // le total à partir des lignes de commande côté objet Java.
            ps.setDouble(1, commande.getMontantTotal());
            ps.setInt(2, commande.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du montant total", e);
        }
    }

    /**
     * Marque une commande comme "validée" (validee = TRUE) en base de données.
     * Typiquement appelée quand le processus de commande est finalisé.
     *
     * @param commande la commande à valider (doit avoir un ID existant)
     */
    public void valider(Commande commande) {
        String sql = "UPDATE commande SET validee = TRUE WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la validation de la commande", e);
        }
    }

    /**
     * Récupère la liste complète de toutes les commandes présentes en base.
     * Note : cette méthode ne charge PAS les lignes de commande associées
     * (voir trouverParId pour une commande avec ses lignes détaillées).
     *
     * @return une liste de toutes les commandes (vide si aucune commande)
     */
    public List<Commande> getTous() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // On parcourt chaque ligne du résultat SQL et on la transforme
            // en objet Commande grâce à la méthode mapper()
            while (rs.next()) {
                commandes.add(mapper(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes", e);
        }
        return commandes;
    }

    /**
     * Recherche une commande par son ID, et charge en plus ses lignes de commande
     * (contrairement à getTous() qui ne charge pas les lignes, pour rester léger).
     *
     * @param id l'identifiant de la commande recherchée
     * @return la commande trouvée (avec ses lignes remplies), ou null si aucune commande ne correspond
     */
    public Commande trouverParId(int id) {
        String sql = "SELECT * FROM commande WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande commande = mapper(rs);
                    // On charge en plus les lignes de commande associées à cette commande
                    commande.getLignes().addAll(getLignesDeCommande(commande));
                    return commande;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la commande", e);
        }
        return null; // aucune commande trouvée avec cet ID
    }

    /**
     * Récupère toutes les lignes de commande (produit + quantité) associées à une commande donnée.
     * Pour chaque ligne, va chercher l'objet Produit complet via ProduitRepository
     * (au lieu de stocker uniquement l'ID du produit).
     *
     * @param commande la commande dont on veut récupérer les lignes (doit avoir un ID)
     * @return la liste des lignes de commande (vide si la commande n'a aucun produit)
     */
    public List<LigneCommande> getLignesDeCommande(Commande commande) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM ligne_commande WHERE commande_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Pour chaque ligne, on ne stocke pas juste le produit_id :
                    // on va chercher l'objet Produit complet (nom, prix, etc.)
                    Produit produit = produitRepository.trouverParId(rs.getInt("produit_id"));
                    lignes.add(new LigneCommande(rs.getInt("id"), produit, rs.getInt("quantite")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des lignes de commande", e);
        }
        return lignes;
    }

    /**
     * Convertit une ligne de ResultSet (issue de la table "commande") en objet Commande.
     * Va également chercher l'objet Client complet associé via ClientRepository,
     * plutôt que de garder seulement le client_id.
     *
     * @param rs le curseur positionné sur une ligne de résultat SQL valide
     * @return l'objet Commande correspondant à la ligne courante du ResultSet
     * @throws SQLException si une colonne attendue est absente ou mal typée
     */
    private Commande mapper(ResultSet rs) throws SQLException {
        // Récupère l'objet Client complet à partir du client_id stocké dans la commande
        Client client = clientRepository.findById(rs.getInt("client_id"));

        return new Commande(
                rs.getInt("id"),
                rs.getString("numero"),
                rs.getDate("date_commande").toLocalDate(), // conversion java.sql.Date -> LocalDate
                rs.getDouble("montant_total"),
                rs.getBoolean("validee"),
                client);
    }

    /**
     * Vérifie si une commande existe déjà en base avec ce numéro exact.
     * Utile pour éviter les doublons avant d'insérer une nouvelle commande.
     *
     * @param numero le numéro de commande à vérifier
     * @return true si une commande avec ce numéro existe déjà, false sinon
     */
    public boolean existeParNumero(String numero) {
        String sql = "SELECT 1 FROM commande WHERE numero = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                // rs.next() renvoie true s'il existe au moins une ligne correspondante
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du doublon numéro de commande", e);
        }
    }
}