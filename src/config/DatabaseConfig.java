package config;  
// ↑ Ce fichier est dans le dossier config

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// ↑ On importe les outils pour se connecter à MySQL

public class DatabaseConfig{
    
    // L'URL pour se connecter à MySQL
    // "localhost" = le serveur est sur votre ordinateur
    // "3306" = le port par défaut de MySQL
    // "gestion_commerciale" = le nom de votre base de données
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_commercialeAll?useSSL=false&serverTimezone=UTC";
    
    // L'utilisateur de MySQL (par défaut 'root')
    private static final String USER = "root";
    
    
    private static final String PASSWORD = "";

    // Ce bloc s'exécute automatiquement quand la classe est chargée
    static {
        try {
            // On charge le driver MySQL (le traducteur entre Java et MySQL)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL charge avec succes");
        } catch (ClassNotFoundException e) {
            System.err.println("ERREUR : Driver MySQL non trouve");
            System.err.println("Ajoutez le driver dans les dependances");
        }
    }

    // Cette méthode retourne une connexion à la base de données
    // C'est comme un "câble" qui relie Java à MySQL
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}