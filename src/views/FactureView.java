package views;

import entities.Facture;
import services.FactureService;

import java.util.List;
import java.util.Scanner;

public class FactureView {

    private FactureService service;
    private Scanner scanner;

    public FactureView(FactureService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix = 0;
        do {
            System.out.println("\n--- GESTION DES FACTURES ---");
            System.out.println("1. Lister les factures");
            System.out.println("2. Voir une facture par id");
            System.out.println("3. Enregistrer un paiement");
            System.out.println("4. Annuler une facture");
            System.out.println("5. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // vider le tampon

            switch (choix) {
                case 1 -> afficherListe();
                case 2 -> afficherParId();
                case 3 -> enregistrerPaiement();
                case 4 -> annulerFacture();
            }
        } while (choix != 5);
    }

    private void afficherListe() {
        List<Facture> factures = service.listerFactures();
        if (factures.isEmpty()) {
            System.out.println("Aucune facture enregistree.");
            return;
        }
        for (Facture f : factures) {
            System.out.println(f.toChaine());
        }
    }

    private void afficherParId() {
        System.out.print("Id de la facture : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Facture facture = service.trouverParId(id);
        if (facture == null) {
            System.out.println("Facture introuvable.");
        } else {
            System.out.println(facture.toChaine());
        }
    }

    private void enregistrerPaiement() {
        System.out.print("Id de la facture : ");
        int id = scanner.nextInt();
        System.out.print("Montant paye : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        Facture facture = service.enregistrerPaiement(id, montant);
        if (facture != null) {
            System.out.println("Paiement enregistre. Nouveau statut : " + facture.getStatutPaiement());
        }
    }

    private void annulerFacture() {
        System.out.print("Id de la facture a annuler : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Facture facture = service.annulerFacture(id);
        if (facture != null) {
            System.out.println("Facture annulee.");
        }
    }
}