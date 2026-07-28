package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une commande passée par un client.
 * Contient la liste de ses lignes de commande (produits + quantités).
 */
public class Commande {

    private int id;
    private String numero;
    private LocalDate date;
    private double montantTotal;
    private boolean validee;
    private Client client;
    private List<LigneCommande> lignes;

    public Commande(String numero, Client client) {
        this.numero = numero;
        this.date = LocalDate.now();
        this.client = client;
        this.lignes = new ArrayList<>();
        this.montantTotal = 0;
        this.validee = false;
    }

    public Commande(int id, String numero, LocalDate date, double montantTotal, boolean validee, Client client) {
        this.id = id;
        this.numero = numero;
        this.date = date;
        this.montantTotal = montantTotal;
        this.validee = validee;
        this.client = client;
        this.lignes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public boolean isValidee() {
        return validee;
    }

    public void setValidee(boolean validee) {
        this.validee = validee;
    }

    public Client getClient() {
        return client;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    /**
     * Ajoute une ligne en mémoire et recalcule le montant total.
     * L'enregistrement en base est fait séparément par le repository.
     */
    public void ajouterLigne(LigneCommande ligne) {
        lignes.add(ligne);
        recalculerMontantTotal();
    }

    private void recalculerMontantTotal() {
        double total = 0;
        for (LigneCommande ligne : lignes) {
            total += ligne.getSousTotal();
        }
        this.montantTotal = total;
    }

    public String toChaine() {
        return "======================Commande========================" + "\n"
                + "  Id      : " + id + "\n"
                + "  Numéro  : " + numero + "\n"
                + "  Date    : " + date + "\n"
                + "  Client  : " + client.getPrenom() + " " + client.getNom() + "\n"
                + "  Total   : " + montantTotal + " FCFA\n"
                + "  Statut  : " + (validee ? "VALIDEE" : "NON VALIDEE") + "\n"
                + "======================================================";
    }
}
