// Déclare que cette classe fait partie du package "view" (couche présentation / interaction utilisateur)
package views;

// Importe Scanner pour lire les entrées saisies au clavier par l'utilisateur
import java.util.Scanner;

/**
 * redemande tant que la valeur saisie n'est
 * pas un nombre valide.
 */
class Saisie {

    // Lit un entier au clavier, en redemandant tant que la saisie n'est pas un nombre valide
    static int lireEntier(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Affiche le message d'invite sans retour à la ligne
            System.out.print(message);
            // Lit la ligne complète saisie par l'utilisateur
            String texte = scanner.nextLine();
            // Tente de convertir le texte saisi en entier
            try {
                // Retourne directement l'entier si la conversion réussit
                return Integer.parseInt(texte);
            } catch (NumberFormatException e) {
                // La conversion a échoué : affiche un message d'erreur et relance la boucle
                System.out.println("Valeur invalide, veuillez saisir un nombre entier.");
            }
        }
    }

    // Lit un nombre décimal au clavier, en redemandant tant que la saisie n'est pas un nombre valide
    static double lireDouble(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Affiche le message d'invite sans retour à la ligne
            System.out.print(message);
            // Lit la ligne complète saisie par l'utilisateur
            String texte = scanner.nextLine();
            // Tente de convertir le texte saisi en nombre décimal
            try {
                // Retourne directement le nombre décimal si la conversion réussit
                return Double.parseDouble(texte);
            } catch (NumberFormatException e) {
                // La conversion a échoué : affiche un message d'erreur et relance la boucle
                System.out.println("Valeur invalide, veuillez saisir un nombre.");
            }
        }
    }

    // Lit un entier positif ou nul, en redemandant tant que la valeur est négative
    static int lireEntierPositifOuNul(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Réutilise lireEntier pour obtenir un entier syntaxiquement valide
            int valeur = lireEntier(scanner, message);
            // Vérifie que la valeur saisie n'est pas négative
            if (valeur < 0) {
                // Valeur négative refusée : affiche un message d'erreur
                System.out.println("Valeur invalide, veuillez saisir un nombre positif ou nul.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne la valeur car elle est positive ou nulle
            return valeur;
        }
    }

    // Lit un entier strictement positif, en redemandant tant que la valeur est inférieure ou égale à zéro
    static int lireEntierStrictementPositif(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Réutilise lireEntier pour obtenir un entier syntaxiquement valide
            int valeur = lireEntier(scanner, message);
            // Vérifie que la valeur saisie est strictement positive
            if (valeur <= 0) {
                // Valeur nulle ou négative refusée : affiche un message d'erreur
                System.out.println("Valeur invalide, veuillez saisir un nombre strictement positif.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne la valeur car elle est strictement positive
            return valeur;
        }
    }

    // Lit un nombre décimal positif ou nul, en redemandant tant que la valeur est négative
    static double lireDoublePositifOuNul(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Réutilise lireDouble pour obtenir un nombre décimal syntaxiquement valide
            double valeur = lireDouble(scanner, message);
            // Vérifie que la valeur saisie n'est pas négative
            if (valeur < 0) {
                // Valeur négative refusée : affiche un message d'erreur
                System.out.println("Valeur invalide, veuillez saisir un nombre positif ou nul.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne la valeur car elle est positive ou nulle
            return valeur;
        }
    }

    // Lit un nombre décimal strictement positif, en redemandant tant que la valeur est inférieure ou égale à zéro
    static double lireDoubleStrictementPositif(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Réutilise lireDouble pour obtenir un nombre décimal syntaxiquement valide
            double valeur = lireDouble(scanner, message);
            // Vérifie que la valeur saisie est strictement positive
            if (valeur <= 0) {
                // Valeur nulle ou négative refusée : affiche un message d'erreur
                System.out.println("Valeur invalide, veuillez saisir un nombre strictement positif.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne la valeur car elle est strictement positive
            return valeur;
        }
    }

    // Lit un texte au clavier, en redemandant tant que la saisie est vide (espaces seuls compris)
    static String lireTexteNonVide(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Affiche le message d'invite sans retour à la ligne
            System.out.print(message);
            // Lit la ligne saisie et retire les espaces de début/fin
            String texte = scanner.nextLine().trim();
            // Vérifie que la saisie n'est pas vide
            if (texte.isEmpty()) {
                // Saisie vide refusée : affiche un message d'erreur
                System.out.println("Valeur invalide, ce champ ne peut pas être vide.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne le texte car il n'est pas vide
            return texte;
        }
    }

    // Lit un texte non vide composé uniquement de lettres (espaces, tirets et apostrophes tolérés)
    static String lireTexteAlphabetique(Scanner scanner, String message) {
        // Boucle infinie interrompue uniquement par un "return" en cas de saisie valide
        while (true) {
            // Réutilise lireTexteNonVide pour obtenir un texte non vide
            String texte = lireTexteNonVide(scanner, message);
            // Vérifie que le texte ne contient que des lettres, espaces, tirets ou apostrophes (aucun chiffre)
            if (!texte.matches("[\\p{L} '-]+")) {
                // Texte contenant un chiffre ou un caractère invalide : affiche un message d'erreur
                System.out.println("Valeur invalide, veuillez saisir uniquement des lettres.");
                // Relance immédiatement la boucle pour redemander une saisie
                continue;
            }
            // Retourne le texte car il ne contient que des lettres
            return texte;
        }
    }
    // Fin de la classe Saisie

        // Lit un numéro de téléphone valide (uniquement chiffres, espaces, tirets et facultativement un '+')
    static String lireTelephoneValide(Scanner scanner, String message) {
        while (true) {
            String texte = lireTexteNonVide(scanner, message);
            // Expression régulière : autorise le '+' au début, suivi de chiffres, espaces ou tirets
            if (!texte.matches("^\\+?[0-9 '-]+$")) {
                System.out.println("Valeur invalide, veuillez saisir un numéro de téléphone correct.");
                continue;
            }
            return texte;
        }
    }

}
