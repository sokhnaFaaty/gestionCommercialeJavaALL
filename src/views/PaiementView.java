package views;

import entities.Facture;
import entities.Paiement;
import services.FactureService;
import services.PaiementService;

import java.util.Date;
import java.util.Scanner;

public class PaiementView {
    private PaiementService service;
    private FactureService factureService;
    private Scanner scanner;

    public PaiementView(PaiementService service, FactureService factureService, Scanner scanner) {
        this.service = service;
        this.factureService = factureService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix = 0;
        do {
            System.out.println("\n--- GESTION DES PAIEMENTS ---");
            System.out.println("1. Enregistrer un paiement pour une facture");
            System.out.println("2. Afficher tous les paiements");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) {
                System.out.println("\nFactures disponibles :");
                for (Facture f : factureService.listerFactures()) {
                    System.out.println(f.toChaine());
                }

                System.out.print("Entrez l'ID de la facture à payer : ");
                int idFact = scanner.nextInt();
                scanner.nextLine();

                Facture f = factureService.trouverParId(idFact);
                if (f == null) {
                    System.out.println("Facture introuvable.");
                    continue;
                }

                System.out.print("Numéro de reçu de paiement : ");
                String numP = scanner.nextLine();
                System.out.print("Montant versé : ");
                double montant = scanner.nextDouble();
                scanner.nextLine();

                Paiement p = new Paiement(0, numP, montant, new Date(), f);
                if (service.enregistrerPaiement(p)) {
                    System.out.println("Paiement enregistré avec succès !");
                } else {
                    System.out.println("Erreur lors de l'enregistrement du paiement.");
                }

            } else if (choix == 2) {
                System.out.println("\nHistorique global des règlements :");
                for (Paiement p : service.listerTousLesPaiements()) {
                    p.toChaine();
                }
            }
        } while (choix != 3);
    }
}