// Déclare que cette classe fait partie du package "repository" (couche d'accès aux données)
package repositories;

// Importe le Singleton qui fournit la connexion unique à la base de données
import config.ConnexionBD;
// Importe l'entité Produit, l'objet métier principal manipulé par ce repository
import entities.Produit;

// Importe en bloc toutes les classes du JDBC nécessaires (Connection, PreparedStatement, ResultSet, etc.)
import java.sql.*;
// Importe ArrayList, l'implémentation concrète utilisée pour construire les listes de résultats
import java.util.ArrayList;
// Importe List, le type d'interface utilisé pour retourner les collections de produits
import java.util.List;

/**
 * Réalise les opérations SQL liées à la table "produit".
 */
public class ProduitRepository {

    // Récupère la connexion unique partagée par toute l'application via le Singleton
    private Connection connection = ConnexionBD.getInstance().getConnection();

    // Insère un nouveau produit en base de données
    public Produit ajouter(Produit produit) {
        // Requête SQL paramétrée pour insérer un produit avec toutes ses colonnes
        String sql = "INSERT INTO produit (libelle, quantite_stock, prix_unitaire) VALUES (?, ?, ?)";
        // Prépare la requête en demandant à JDBC de renvoyer les clés générées (l'id auto-incrémenté)
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Remplace le premier "?" par le libellé du produit
            ps.setString(1, produit.getLibelle());
            // Remplace le deuxième "?" par la quantité en stock
            ps.setInt(2, produit.getQuantiteEnStock());
            // Remplace le troisième "?" par le prix unitaire
            ps.setDouble(3, produit.getPrixUnitaire());
            // Exécute l'insertion en base de données
            ps.executeUpdate();

            // Ouvre le jeu de résultats contenant les clés générées par l'insertion
            try (ResultSet cles = ps.getGeneratedKeys()) {
                // Se positionne sur la première (et unique) clé générée si elle existe
                if (cles.next()) {
                    // Affecte l'id généré par la base à l'objet produit en mémoire
                    produit.setId(cles.getInt(1));
                }
            }
            // Retourne le produit désormais complet avec son id
            return produit;
        } catch (SQLException e) {
            // Transforme toute erreur SQL en exception non vérifiée avec un message explicite
            throw new RuntimeException("Erreur lors de l'ajout du produit", e);
        }
    }

    // Récupère la liste complète des produits enregistrés en base
    public List<Produit> getTous() {
        // Liste qui contiendra tous les produits récupérés
        List<Produit> produits = new ArrayList<>();
        // Requête SQL simple sans paramètre, sélectionnant toutes les colonnes de la table produit
        String sql = "SELECT * FROM produit";
        // Crée une instruction SQL classique et exécute directement la requête, en fermant automatiquement les ressources
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Parcourt chaque ligne du résultat tant qu'il en existe une suivante
            while (rs.next()) {
                // Transforme la ligne courante en objet Produit et l'ajoute à la liste
                produits.add(mapper(rs));
            }
        } catch (SQLException e) {
            // Transforme toute erreur SQL en exception non vérifiée avec un message explicite
            throw new RuntimeException("Erreur lors de la récupération des produits", e);
        }
        // Retourne la liste complète des produits (vide si la table est vide)
        return produits;
    }

    // Recherche un produit précis à partir de son identifiant technique
    public Produit trouverParId(int id) {
        // Requête SQL paramétrée filtrant sur la colonne id
        String sql = "SELECT * FROM produit WHERE id = ?";
        // Prépare la requête pour éviter les injections SQL
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Remplace le "?" par l'id recherché
            ps.setInt(1, id);
            // Exécute la requête et ouvre le résultat dans un bloc auto-fermant
            try (ResultSet rs = ps.executeQuery()) {
                // Vérifie si une ligne correspond à l'id recherché
                if (rs.next()) {
                    // Transforme la ligne trouvée en objet Produit et la retourne
                    return mapper(rs);
                }
            }
        } catch (SQLException e) {
            // Transforme toute erreur SQL en exception non vérifiée avec un message explicite
            throw new RuntimeException("Erreur lors de la recherche du produit", e);
        }
        // Aucun produit trouvé pour cet id : retourne null
        return null;
    }

    // Recherche les produits dont le libellé contient le texte fourni
    public List<Produit> rechercherParLibelle(String libelle) {
        // Liste qui contiendra les produits correspondant à la recherche
        List<Produit> resultat = new ArrayList<>();
        // Requête SQL paramétrée utilisant LIKE pour une correspondance partielle
        String sql = "SELECT * FROM produit WHERE libelle LIKE ?";
        // Prépare la requête pour éviter les injections SQL
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Entoure le libellé recherché de "%" pour permettre une correspondance partielle
            ps.setString(1, "%" + libelle + "%");
            // Exécute la requête et ouvre le résultat dans un bloc auto-fermant
            try (ResultSet rs = ps.executeQuery()) {
                // Parcourt chaque ligne correspondant au filtre
                while (rs.next()) {
                    // Transforme la ligne courante en objet Produit et l'ajoute au résultat
                    resultat.add(mapper(rs));
                }
            }
        } catch (SQLException e) {
            // Transforme toute erreur SQL en exception non vérifiée avec un message explicite
            throw new RuntimeException("Erreur lors de la recherche par libellé", e);
        }
        // Retourne la liste des produits correspondant à la recherche (vide si aucun résultat)
        return resultat;
    }

    /**
     * Met à jour le stock d'un produit (utilisé après un retrait de stock).
     */
    public void mettreAJourStock(Produit produit) {
        // Requête SQL paramétrée pour mettre à jour uniquement la quantité en stock
        String sql = "UPDATE produit SET quantite_stock = ? WHERE id = ?";
        // Prépare la requête de mise à jour
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Remplace le premier "?" par la nouvelle quantité en stock
            ps.setInt(1, produit.getQuantiteEnStock());
            // Remplace le deuxième "?" par l'id du produit à mettre à jour
            ps.setInt(2, produit.getId());
            // Exécute la mise à jour en base de données
            ps.executeUpdate();
        } catch (SQLException e) {
            // Transforme toute erreur SQL en exception non vérifiée avec un message explicite
            throw new RuntimeException("Erreur lors de la mise à jour du stock", e);
        }
    }

    // Transforme une ligne du ResultSet en objet Produit
    private Produit mapper(ResultSet rs) throws SQLException {
        // Construit un nouvel objet Produit à partir des colonnes de la ligne courante du ResultSet
        return new Produit(
                rs.getInt("id"),
                rs.getString("libelle"),
                rs.getInt("quantite_stock"),
                rs.getDouble("prix_unitaire")
        );
    }
    // Fin de la classe ProduitRepository
}
