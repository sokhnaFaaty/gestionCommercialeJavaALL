package services;

import entities.Paiement;
import entities.Facturation;
import repositories.PaiementRepository;
import java.util.ArrayList;
import java.util.List;

public class PaiementService {
    private PaiementRepository paiementRepository;
    private FacturationService facturationService;

    public PaiementService(FacturationService facturationService) {
        this.paiementRepository = new PaiementRepository();
        this.facturationService = facturationService;
    }

    // Enregistrer un paiement et mettre à jour le statut de la facture liée
    public boolean enregistrerPaiement(Paiement p) {
        Facturation facture = p.getFacture();
        if (facture == null) {
            return false;
        }

        //  Ajouter le paiement à la facture
        facture.addPaiement(p);

        // Calculer et assigner le statut selon la logique métier
        p.setStatut(facturationService.calculerStatutFacture(facture));

        //  Sauvegarder dans la base de données via le repository
        paiementRepository.save(p);
        
        return true;
    }

    public List<Paiement> listerTousLesPaiements() {
        return paiementRepository.findAll();
    }

    // Afficher les paiements d'une facture spécifique
    public List<Paiement> listerPaiementsParFacture(int idFacture) {
        Facturation f = facturationService.rechercherParId(idFacture);
        if (f != null) {
            return f.getPaiements();
        }
        return new ArrayList<>();
    }
}