// Déclare que cette classe fait partie du package "view" (couche présentation / interaction utilisateur)
package view;

// Importe l'entité Produit affichée et saisie par cette vue
import entities.Produit;
// Importe List, le type d'interface utilisé pour recevoir les collections de produits à afficher
import java.util.List;
// Importe Scanner pour lire les entrées saisies au clavier par l'utilisateur
import java.util.Scanner;

// Regroupe toutes les interactions console (saisie et affichage) liées aux produits
public class ProduitView {

    // Scanner unique utilisé pour lire toutes les saisies clavier de cette vue
    private Scanner scanner = new Scanner(System.in);

    // Demande à l'utilisateur les informations d'un nouveau produit et construit l'objet correspondant
    public Produit saisirProduit() {
        // Lit le libellé saisi par l'utilisateur : non vide et composé uniquement de lettres
        String libelle = Saisie.lireTexteAlphabetique(scanner, "Libellé : ");
        // Demande et valide la quantité en stock (doit être positive ou nulle)
        int quantite = Saisie.lireEntierPositifOuNul(scanner, "Quantité en stock : ");
        // Demande et valide le prix unitaire (doit être positif ou nul)
        double prix = Saisie.lireDoublePositifOuNul(scanner, "Prix unitaire : ");
        // Construit et retourne un nouvel objet Produit avec les valeurs saisies
        return new Produit(libelle, quantite, prix);
    }

    // Demande à l'utilisateur l'id d'un produit, en validant que c'est bien un entier
    public int saisirId() {
        return Saisie.lireEntier(scanner, "Id du produit : ");
    }

    // Demande à l'utilisateur un libellé de produit à rechercher
    public String saisirLibelle() {
        // Affiche l'invite de recherche
        System.out.print("Libellé à rechercher : ");
        // Lit et retourne le texte saisi par l'utilisateur
        return scanner.nextLine();
    }

    // Affiche la liste des produits fournie, ou un message si elle est vide
    public void afficherProduits(List<Produit> produits) {
        // Vérifie si la liste de produits est vide
        if (produits.isEmpty()) {
            // Aucun produit à afficher : informe l'utilisateur et sort de la méthode
            System.out.println("Aucun produit enregistré.");
            return;
        }
        // Parcourt chaque produit de la liste
        for (Produit produit : produits) {
            // Affiche la représentation textuelle du produit courant
            System.out.println(produit.toChaine());
        }
    }
    // Fin de la classe ProduitView
}
