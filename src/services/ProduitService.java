// Déclare que cette classe fait partie du package "service" (couche métier)
package service;

// Importe l'entité Produit manipulée par ce service
import entities.Produit;
// Importe le repository utilisé pour accéder aux données des produits
import repository.ProduitRepository;
// Importe List, le type d'interface utilisé pour retourner les collections de produits
import java.util.List;

// Sert d'intermédiaire entre les vues et le repository pour tout ce qui concerne les produits
public class ProduitService {

    // Repository utilisé pour toutes les opérations d'accès aux données des produits
    private ProduitRepository produitRepository = new ProduitRepository();

    // Délègue l'ajout d'un produit au repository et retourne le produit créé (avec son id)
    public Produit ajouterProduit(Produit produit) {
        return produitRepository.ajouter(produit);
    }

    // Délègue la récupération de tous les produits au repository
    public List<Produit> listerProduits() {
        return produitRepository.getTous();
    }

    // Délègue la recherche d'un produit par son id au repository
    public Produit trouverParId(int id) {
        return produitRepository.trouverParId(id);
    }

    // Délègue la recherche de produits par libellé au repository
    public List<Produit> rechercherParLibelle(String libelle) {
        return produitRepository.rechercherParLibelle(libelle);
    }
    // Fin de la classe ProduitService
}
