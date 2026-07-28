package repository;

import entities.Client;
import config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    
    // La configuration de la base de donnees
    private DatabaseConfig dbConfig;

    // Constructeur : initialise la configuration et cree la table
    public ClientRepository() {
        this.dbConfig = new DatabaseConfig();
        initTable();
    }

    // Cree la table clients si elle n'existe pas
    private void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS clients (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nom VARCHAR(100) NOT NULL,
                prenom VARCHAR(100) NOT NULL,
                telephone VARCHAR(20) UNIQUE NOT NULL
            )
        """;
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table clients prete");
        } catch (SQLException e) {
            System.err.println("Erreur creation table clients: " + e.getMessage());
        }
    }

    // Ajouter un client dans la base de donnees
    public void save(Client client) {
        String sql = "INSERT INTO clients (nom, prenom, telephone) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setString(3, client.getTelephone());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
            }
            
            System.out.println("Client ajoute (ID: " + client.getId() + ")");
            
        } catch (SQLException e) {
            System.err.println("Erreur ajout client: " + e.getMessage());
        }
    }

    // Recuperer tous les clients
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY id";
        
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone")
                );
                clients.add(client);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur liste clients: " + e.getMessage());
        }
        
        return clients;
    }

    // Rechercher un client par son ID
    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur recherche client: " + e.getMessage());
        }
        return null;
    }

    // Rechercher un client par telephone
    public Client findByTelephone(String telephone) {
        String sql = "SELECT * FROM clients WHERE telephone = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, telephone);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur recherche telephone: " + e.getMessage());
        }
        return null;
    }

    // Supprimer un client par son ID
    public void deleteById(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Client supprime (ID: " + id + ")");
            } else {
                System.out.println("Aucun client avec l'ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur suppression client: " + e.getMessage());
        }
    }

    // Mettre a jour un client
    public void update(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, telephone = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setString(3, client.getTelephone());
            pstmt.setInt(4, client.getId());
            pstmt.executeUpdate();
            
            System.out.println("Client mis a jour (ID: " + client.getId() + ")");
            
        } catch (SQLException e) {
            System.err.println("Erreur mise a jour client: " + e.getMessage());
        }
    }
}