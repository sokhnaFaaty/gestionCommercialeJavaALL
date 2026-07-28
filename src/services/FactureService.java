package services;

import entities.Facture;
import entities.Commande;
import repositories.FactureRepository;

import java.util.List;

public class FactureService {

    private FactureRepository factureRepository = new FactureRepository();

    // Genere une facture a partir d'une commande (idealement deja validee)
    public Facture creerFacture(String numeroFacture, Commande commande) {
        Facture facture = new Facture(numeroFacture, commande);
        factureRepository.save(facture);
        return facture;
    }

    // Delegue la recuperation de toutes les factures au repository
    public List<Facture> listerFactures() {
        return factureRepository.findAll();
    }

    // Delegue la recherche d'une facture par id au repository
    public Facture trouverParId(int id) {
        return factureRepository.findById(id);
    }

    // Delegue la recherche des factures liees a une commande
    public List<Facture> trouverParCommande(int commandeId) {
        return factureRepository.findByCommandeId(commandeId);
    }

    // Enregistre un paiement (partiel ou total) sur une facture existante
    public Facture enregistrerPaiement(int factureId, double montant) {
        Facture facture = factureRepository.findById(factureId);
        if (facture == null) {
            System.out.println("Facture introuvable (ID: " + factureId + ")");
            return null;
        }
        facture.enregistrerPaiement(montant);
        factureRepository.updatePaiement(facture);
        return facture;
    }

    // Annule une facture existante
    public Facture annulerFacture(int factureId) {
        Facture facture = factureRepository.findById(factureId);
        if (facture == null) {
            System.out.println("Facture introuvable (ID: " + factureId + ")");
            return null;
        }
        facture.annuler();
        factureRepository.updatePaiement(facture);
        return facture;
    }
}