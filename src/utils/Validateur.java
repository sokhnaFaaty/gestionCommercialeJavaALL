package utils;

import java.util.regex.Pattern;

/**
 * Regroupe toutes les règles de validation de l'application.
 * Ne contient aucune saisie console : ce sont des vérifications pures,
 * réutilisables telles quelles si l'application passe un jour en version web.
 */
public class Validateur {

    // Bornes pour éviter des valeurs absurdes qui pourraient casser le programme
    public static final int QUANTITE_MIN = 1;
    public static final int QUANTITE_MAX = 100_000;
    public static final double PRIX_MIN = 1;
    public static final double PRIX_MAX = 10_000_000;
    public static final double MONTANT_MIN = 0.01;
    public static final double MONTANT_MAX = 1_000_000_000.0;
    public static final int ID_MAX = 1_000_000;

    // Numéro Orange Sénégal : préfixe 77 ou 78, 9 chiffres, indicatif +221
    // optionnel
    private static final Pattern TELEPHONE_ORANGE = Pattern.compile("^(\\+221)?7[78][0-9]{7}$");

    public static boolean estNonVide(String valeur) {
        return valeur != null && !valeur.trim().isEmpty();
    }

    public static boolean estTelephoneOrangeValide(String telephone) {
        return telephone != null && TELEPHONE_ORANGE.matcher(telephone.trim()).matches();
    }

    public static boolean estEntierDansBornes(int valeur, int min, int max) {
        return valeur >= min && valeur <= max;
    }

    public static boolean estDoubleDansBornes(double valeur, double min, double max) {
        return valeur >= min && valeur <= max;
    }
}