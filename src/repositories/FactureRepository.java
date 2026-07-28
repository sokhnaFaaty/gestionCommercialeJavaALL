package repositories;

import entities.Facture;
import enums.StatutPaiement;
import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FactureRepository {

    // La configuration de la base de donnees
    private DatabaseConfig dbConfig;

    // Constructeur : initialise la configuration et cree la table
    public FactureRepository() {
        this.dbConfig = new DatabaseConfig();
        initTable();
    }

    // Cree la table factures si elle n'existe pas
    private void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS factures (
                id INT PRIMARY KEY AUTO_INCREMENT,
                numero_facture VARCHAR(50) UNIQUE NOT NULL,
                date_facture DATE NOT NULL,
                commande_id INT NOT NULL,
                numero_commande VARCHAR(50) NOT NULL,
                montant_total DOUBLE NOT NULL,
                montant_paye DOUBLE NOT NULL DEFAULT 0,
                statut_paiement VARCHAR(20) NOT NULL
            )
        """;

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table factures prete");
        } catch (SQLException e) {
            System.err.println("Erreur creation table factures: " + e.getMessage());
        }
    }

    // Enregistrer une nouvelle facture
    public void save(Facture facture) {
        String sql = "INSERT INTO factures (numero_facture, date_facture, commande_id, numero_commande, montant_total, montant_paye, statut_paiement) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, facture.getNumeroFacture());
            pstmt.setDate(2, Date.valueOf(facture.getDateFacture()));
            pstmt.setInt(3, facture.getCommandeId());
            pstmt.setString(4, facture.getNumeroCommande());
            pstmt.setDouble(5, facture.getMontantTotal());
            pstmt.setDouble(6, facture.getMontantPaye());
            pstmt.setString(7, facture.getStatutPaiement().name());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                facture.setId(rs.getInt(1));
            }

            System.out.println("Facture enregistree (ID: " + facture.getId() + ")");

        } catch (SQLException e) {
            System.err.println("Erreur ajout facture: " + e.getMessage());
        }
    }

    // Recuperer toutes les factures
    public List<Facture> findAll() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures ORDER BY id";

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                factures.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur liste factures: " + e.getMessage());
        }

        return factures;
    }

    // Rechercher une facture par son id
    public Facture findById(int id) {
        String sql = "SELECT * FROM factures WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erreur recherche facture: " + e.getMessage());
        }
        return null;
    }

    // Rechercher toutes les factures liees a une commande
    public List<Facture> findByCommandeId(int commandeId) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures WHERE commande_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commandeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                factures.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur recherche factures par commande: " + e.getMessage());
        }
        return factures;
    }

    // Mettre a jour le paiement et le statut d'une facture existante
    public void updatePaiement(Facture facture) {
        String sql = "UPDATE factures SET montant_paye = ?, statut_paiement = ? WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, facture.getMontantPaye());
            pstmt.setString(2, facture.getStatutPaiement().name());
            pstmt.setInt(3, facture.getId());
            pstmt.executeUpdate();

            System.out.println("Facture mise a jour (ID: " + facture.getId() + ")");

        } catch (SQLException e) {
            System.err.println("Erreur mise a jour facture: " + e.getMessage());
        }
    }

    // Construit un objet Facture a partir d'une ligne de resultat SQL
    private Facture mapResultSet(ResultSet rs) throws SQLException {
        return new Facture(
            rs.getInt("id"),
            rs.getString("numero_facture"),
            rs.getDate("date_facture").toLocalDate(),
            rs.getInt("commande_id"),
            rs.getString("numero_commande"),
            rs.getDouble("montant_total"),
            rs.getDouble("montant_paye"),
            StatutPaiement.valueOf(rs.getString("statut_paiement"))
        );
    }
}