package enums;

public enum StatutProduit {
    DISPONIBLE("Disponible"),
    RUPTURE_STOCK("Rupture de stock"),
    DISCONTINUE("Discontinué"),
    EN_PROMOTION("En promotion");

    private final String libelle;

    StatutProduit(String libelle) {
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