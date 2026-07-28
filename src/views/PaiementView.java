package views;

import entities.Facturation;
import entities.Paiement;
import services.FacturationService;
import services.PaiementService;

import java.util.Date;
import java.util.Scanner;

public class PaiementView {
    private PaiementService service;
    private FacturationService facturationService;
    private Scanner scanner;

    public PaiementView(PaiementService service, FacturationService facturationService, Scanner scanner) {
        this.service = service;
        this.facturationService = facturationService;
        this.scanner = scanner;
    }

    public void afficherMenu() {
        int choix = 0;
        do {
            System.out.println("\n--- GESTION DES PAIEMENTS ---");
            System.out.println("1. Enregistrer un paiement pour une facture");
            System.out.println("2. Afficher les paiements d'une facture");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) {
                System.out.println("\nFactures disponibles :");
                for (Facturation f : facturationService.listerFactures()) {
                    f.toChaine();
                }

                System.out.print("Entrez l'ID de la facture à payer : ");
                int idFact = scanner.nextInt();
                scanner.nextLine();

                Facturation f = facturationService.rechercherParId(idFact);
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
                System.out.print("Entrez l'ID de la facture concernée : ");
                int idFact = scanner.nextInt();
                scanner.nextLine();

                System.out.println("\nHistorique des règlements pour cette facture :");
                for (Paiement p : service.listerPaiementsParFacture(idFact)) {
                    p.toChaine();
                }
            }
        } while (choix != 3);
    }
}