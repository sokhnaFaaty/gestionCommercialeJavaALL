// Déclare que cette classe fait partie du package "entities"
package entities;

/**
 * Représente un produit vendu par le magasin.
 */
public class Produit {

    // Identifiant technique du produit, généré par la base de données
    private int id;
    // Libellé (nom) du produit
    private String libelle;
    // Quantité disponible en stock
    private int quantiteEnStock;
    // Prix de vente unitaire du produit
    private double prixUnitaire;

    // Constructeur utilisé lors de la création d'un nouveau produit (pas encore d'id en base)
    public Produit(String libelle, int quantiteEnStock, double prixUnitaire) {
        // Mémorise le libellé fourni
        this.libelle = libelle;
        // Mémorise la quantité en stock fournie
        this.quantiteEnStock = quantiteEnStock;
        // Mémorise le prix unitaire fourni
        this.prixUnitaire = prixUnitaire;
    }

    // Constructeur utilisé pour reconstruire un produit à partir de données déjà en base
    public Produit(int id, String libelle, int quantiteEnStock, double prixUnitaire) {
        // Réutilise l'id existant venant de la base de données
        this.id = id;
        // Réutilise le libellé existant
        this.libelle = libelle;
        // Réutilise la quantité en stock existante
        this.quantiteEnStock = quantiteEnStock;
        // Réutilise le prix unitaire existant
        this.prixUnitaire = prixUnitaire;
    }

    // Retourne l'identifiant technique du produit
    public int getId() {
        return id;
    }

    // Modifie l'identifiant technique (utilisé après l'insertion en base)
    public void setId(int id) {
        this.id = id;
    }

    // Retourne le libellé du produit
    public String getLibelle() {
        return libelle;
    }

    // Retourne la quantité actuellement en stock
    public int getQuantiteEnStock() {
        return quantiteEnStock;
    }

    // Modifie la quantité en stock (utilisé après une vente ou un réapprovisionnement)
    public void setQuantiteEnStock(int quantiteEnStock) {
        this.quantiteEnStock = quantiteEnStock;
    }

    // Retourne le prix unitaire du produit
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    // Construit une représentation textuelle lisible du produit (pour l'affichage console)
    public String toChaine() {
        // Concatène chaque information du produit sur une ligne distincte
        return "Produit :" + "\n"
                + "  Id       : " + id + "\n"
                + "  Libellé  : " + libelle + "\n"
                + "  Stock    : " + quantiteEnStock + "\n"
                + "  Prix     : " + prixUnitaire + " FCFA";
    }
    // Fin de la classe Produit
}
