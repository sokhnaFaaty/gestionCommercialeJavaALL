package repositories;

import entities.Paiement;
import enums.StatutPaiement;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementRepository {

    public void save(Paiement p) {
        String sql = "INSERT INTO paiement (numero, montant_verse, date_paiement, facture_id, statut_paiement) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
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
            System.out.println("Erreur lors de la sauvegarde du paiement : " + e.getMessage());
        }
    }

    public List<Paiement> findAll() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paiement p = new Paiement(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDouble("montant_verse"),
                    rs.getDate("date_paiement"),
                    null
                );
                p.setStatut(StatutPaiement.valueOf(rs.getString("statut_paiement")));
                paiements.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
        return paiements;
    }
}