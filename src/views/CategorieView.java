package views;

import entities.Categorie;
import java.util.List;
import java.util.Scanner;

public class CategorieView {
    private Scanner scanner;

    public CategorieView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int afficherMenu() {
        System.out.println("\n--- GESTION DES CATEGORIES ---");
        System.out.println("1. Ajouter une catégorie");
        System.out.println("2. Lister les catégories");
        System.out.println("3. Retour au menu principal");
        
        // Utilisation de Saisie pour s'assurer que le choix du menu est bien un entier valide
        return Saisie.lireEntier(scanner, "Votre choix : ");
    }

    public Categorie saisirCategorie() {
        System.out.println("\n--- AJOUT D'UNE CATEGORIE ---");
        
        // Utilisation de Saisie pour bloquer tant que ce ne sont pas des lettres non vides
        String libelle = Saisie.lireTexteAlphabetique(scanner, "Entrez le libellé de la catégorie (Obligatoire) : ");
        
        return new Categorie(0, libelle);
    }

    public void afficherListe(List<Categorie> categories) {
        if (categories == null || categories.isEmpty()) {
            System.out.println("Aucune catégorie à afficher.");
            return;
        }
        System.out.println("\nListe des catégories :");
        for (Categorie cat : categories) {
            cat.toChaine();
        }
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
