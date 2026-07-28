package entities;

import java.util.Date;
import enums.StatutPaiement;

public class Paiement {
    private int id;
    private String numero;
    private double montantVerse;
    private Date date;
    private Facture facture;
    private StatutPaiement statut;

    public Paiement() {
        this.statut = StatutPaiement.EN_ATTENTE;
    }

    public Paiement(int id, String numero, double montantVerse, Date date, Facture facture) {
        this.id = id;
        this.numero = numero;
        this.montantVerse = montantVerse;
        this.date = date;
        this.facture = facture;
        this.statut = StatutPaiement.EN_ATTENTE;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(double montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public StatutPaiement getStatut() {
        return statut;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    public void toChaine() {
        System.out.println("id=" + this.id + ", numero=" + this.numero + ", montantVerse=" + this.montantVerse
                + ", date=" + this.date + ", statut=" + this.statut);
    }
}