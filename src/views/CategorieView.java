package views;

import java.util.Scanner;

public class CategorieView {
    private CategorieService service;
    private Scanner scanner;

    public CategorieView(CategorieProduitService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix = 0;
        do {
            System.out.println("\n--- GESTION DES CATEGORIES ---");
            System.out.println("1. Ajouter une catégorie");
            System.out.println("2. Lister les catégories");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // vider le tampon

            if (choix == 1) {
                String libelle = "";
                while (libelle.trim().isEmpty()) {
                    System.out.print("Entrez le libellé de la catégorie (Obligatoire) : ");
                    libelle = scanner.nextLine();
                }
                
                service.ajouterCategorie(new Categorie(0, libelle));
                System.out.println("Catégorie ajoutée avec succès !");
            } else if (choix == 2) {
                System.out.println("\nListe des catégories :");
                for (Categorie cat : service.listerCategories()) {
                    cat.toChaine();
                }
            }
        } while (choix != 3);
    }
}
