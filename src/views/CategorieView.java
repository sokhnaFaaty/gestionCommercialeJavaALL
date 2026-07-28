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
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Vider le tampon
        return choix;
    }

    public Categorie saisirCategorie() {
        String libelle = "";
        while (libelle.trim().isEmpty()) {
            System.out.print("Entrez le libellé de la catégorie (Obligatoire) : ");
            libelle = scanner.nextLine();
        }
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
