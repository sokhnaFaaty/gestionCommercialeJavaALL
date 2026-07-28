package repositories;

import entities.Paiement;
import entities.Facture;
import enums.StatutPaiement;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementRepository {

    private FactureRepository factureRepository;

    public PaiementRepository() {
        this.factureRepository = new FactureRepository();
        initTable();
    }

    private void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS paiement (
                id INT PRIMARY KEY AUTO_INCREMENT,
                numero VARCHAR(50) NOT NULL,
                montant_verse DOUBLE NOT NULL,
                date_paiement DATE NOT NULL,
                facture_id INT NOT NULL,
                statut_paiement VARCHAR(30) NOT NULL,
                FOREIGN KEY (facture_id) REFERENCES factures(id) ON DELETE CASCADE
            )
        """;

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erreur création table paiement : " + e.getMessage());
        }
    }

    public void save(Paiement p) {
        String sql = "INSERT INTO paiement (numero, montant_verse, date_paiement, facture_id, statut_paiement) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, p.getNumero());
            pstmt.setDouble(2, p.getMontantVerse());
            pstmt.setDate(3, new java.sql.Date(p.getDate().getTime()));
            pstmt.setInt(4, p.getFacture().getId());
            pstmt.setString(5, p.getStatut().name());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du paiement : " + e.getMessage());
        }
    }

    public List<Paiement> findAll() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement ORDER BY id DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facture facture = factureRepository.findById(rs.getInt("facture_id"));

                Paiement p = new Paiement(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("montant_verse"),
                    rs.getDate("date_paiement"),
                    facture
                );
                p.setStatut(StatutPaiement.valueOf(rs.getString("statut_paiement")));
                paiements.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
        return paiements;
    }
}