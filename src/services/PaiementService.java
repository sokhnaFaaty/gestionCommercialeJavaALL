package services;

import entities.Paiement;
import entities.Facture;
import repositories.PaiementRepository;
import java.util.ArrayList;
import java.util.List;

public class PaiementService {
    private PaiementRepository paiementRepository;
    private FactureService factureService;

    public PaiementService(FactureService factureService) {
        this.paiementRepository = new PaiementRepository();
        this.factureService = factureService;
    }

    // Enregistrer un paiement et mettre à jour le montant/statut de la facture
    public boolean enregistrerPaiement(Paiement p) {
        Facture facture = p.getFacture();
        if (facture == null) {
            return false;
        }

        // Mettre à jour la facture dans la base de données via FactureService
        Facture factureAjour = factureService.enregistrerPaiement(facture.getId(), p.getMontantVerse());
        if (factureAjour == null) {
            return false;
        }

        // Assigner le statut calculé au paiement
        p.setStatut(factureAjour.getStatutPaiement());

        // Sauvegarder le paiement dans la base
        paiementRepository.save(p);

        return true;
    }

    public List<Paiement> listerTousLesPaiements() {
        return paiementRepository.findAll();
    }
}