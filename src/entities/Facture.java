package entities;

import java.time.LocalDate;
import enums.StatutPaiement;

/**
 * Représente la facture émise pour une commande.
 * La facture fige la référence et le montant de la commande au moment de sa création
 * (elle ne recharge pas l'objet Commande complet depuis la base, seulement son id et son numéro).
 */
public class Facture {

    private int id;
    private String numeroFacture;
    private LocalDate dateFacture;
    private int commandeId;
    private String numeroCommande;
    private double montantTotal;
    private double montantPaye;
    private StatutPaiement statutPaiement;

    // Constructeur utilisé pour créer une nouvelle facture à partir d'une commande existante
    public Facture(String numeroFacture, Commande commande) {
        this.numeroFacture = numeroFacture;
        this.dateFacture = LocalDate.now();
        this.commandeId = commande.getId();
        this.numeroCommande = commande.getNumero();
        this.montantTotal = commande.getMontantTotal();
        this.montantPaye = 0;
        this.statutPaiement = StatutPaiement.EN_ATTENTE;
    }

    // Constructeur utilisé pour reconstruire une facture depuis la base de données
    public Facture(int id, String numeroFacture, LocalDate dateFacture, int commandeId,
                   String numeroCommande, double montantTotal, double montantPaye,
                   StatutPaiement statutPaiement) {
        this.id = id;
        this.numeroFacture = numeroFacture;
        this.dateFacture = dateFacture;
        this.commandeId = commandeId;
        this.numeroCommande = numeroCommande;
        this.montantTotal = montantTotal;
        this.montantPaye = montantPaye;
        this.statutPaiement = statutPaiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public double getMontantRestant() {
        return montantTotal - montantPaye;
    }

    // Enregistre un paiement (partiel ou total) et met à jour le statut automatiquement
    public void enregistrerPaiement(double montant) {
        this.montantPaye += montant;
        if (montantPaye <= 0) {
            statutPaiement = StatutPaiement.EN_ATTENTE;
        } else if (montantPaye >= montantTotal) {
            statutPaiement = StatutPaiement.PAYE;
        } else {
            statutPaiement = StatutPaiement.PARTIEL;
        }
    }

    public void annuler() {
        statutPaiement = StatutPaiement.ANNULE;
    }

    public String toChaine() {
        return "======================Facture========================" + "\n"
                + "  Id         : " + id + "\n"
                + "  Numéro     : " + numeroFacture + "\n"
                + "  Date       : " + dateFacture + "\n"
                + "  Commande   : " + numeroCommande + "\n"
                + "  Total      : " + montantTotal + " FCFA\n"
                + "  Payé       : " + montantPaye + " FCFA\n"
                + "  Restant    : " + getMontantRestant() + " FCFA\n"
                + "  Statut     : " + statutPaiement + "\n"
                + "======================================================";
    }
}