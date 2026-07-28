package entities;

/**
 * Une ligne de commande associe un produit à une quantité commandée.
 */
public class LigneCommande {

    private int id;
    private Produit produit;
    private int quantite;

    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public LigneCommande(int id, Produit produit, int quantite) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getSousTotal() {
        return produit.getPrixUnitaire() * quantite;
    }

    public String toChaine() {
        return  "=============================================\n"
            + " Libelle " + produit.getLibelle() +"\n"
            + " Quantité " + quantite + " = " + getSousTotal() + " FCFA" + "\n"
            + "==================================================";
    }
}
