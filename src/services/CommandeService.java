package services;

import entities.Commande;
import entities.LigneCommande;
import entities.Produit;
import repositories.CommandeRepository;
import repositories.ProduitRepository;
import java.util.List;

public class CommandeService {

    private CommandeRepository commandeRepository = new CommandeRepository();
    private ProduitRepository produitRepository = new ProduitRepository();

    /**
     * Crée une commande, sauf si le numéro existe déjà (doublon).
     */
    public Commande creerCommande(Commande commande) {
        if (commandeRepository.existeParNumero(commande.getNumero())) {
            return null;
        }
        return commandeRepository.ajouter(commande);
    }

    public boolean ajouterProduitACommande(Commande commande, Produit produit, int quantite) {
        if (quantite > produit.getQuantiteEnStock()) {
            return false;
        }
        produit.setQuantiteEnStock(produit.getQuantiteEnStock() - quantite);
        produitRepository.mettreAJourStock(produit);

        LigneCommande ligne = new LigneCommande(produit, quantite);
        commande.ajouterLigne(ligne);
        commandeRepository.ajouterLigne(commande, ligne);
        return true;
    }

    public boolean validerCommande(Commande commande) {
        if (commande.getLignes().isEmpty()) {
            return false;
        }
        commande.setValidee(true);
        commandeRepository.valider(commande);
        return true;
    }

    public List<Commande> listerCommandes() {
        return commandeRepository.getTous();
    }

    public Commande trouverParId(int id) {
        return commandeRepository.trouverParId(id);
    }
}