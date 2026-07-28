package enums;

public enum StatutPaiement {
    EN_ATTENTE("En attente"),
    PAYE("Payé"),
    PARTIEL("Partiellement payé"),
    ANNULE("Annulé");

    private final String libelle;

    StatutPaiement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}