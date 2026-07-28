package repositories;

import entities.Categorie;

import config.DatabaseConfig;  
import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieRepositories {

    public void save(Categorie categorie) {
        String sql = "INSERT INTO categorie (libelle) VALUES (?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, categorie.getLibelle());
            pstmt.executeUpdate();

            // Récupération de l'ID généré automatiquement par MySQL (AUTO_INCREMENT)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categorie.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sauvegarde de la catégorie : " + e.getMessage());
        }
    }

    public List<Categorie> findAll() {

        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categorie cat = new Categorie(
                    rs.getInt("id"),
                    rs.getString("libelle")
                );
                categories.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des catégories : " + e.getMessage());
        }
        return categories;
    }

    public Categorie findById(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Categorie(
                        rs.getInt("id"),
                        rs.getString("libelle")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de la catégorie : " + e.getMessage());
        }
        return null;
    }
}

