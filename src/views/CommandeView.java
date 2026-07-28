// Déclare que cette classe fait partie du package "view" (couche présentation / interaction utilisateur)
package views;

// Importe l'entité Commande, affichée à l'utilisateur
import entities.Commande;
// Importe Validateur, qui fournit les constantes de bornes (ID_MAX, QUANTITE_MIN, QUANTITE_MAX)
import utils.Validateur;
// Importe List pour afficher une collection de commandes
import java.util.List;
// Importe Scanner, nécessaire pour lire les entrées clavier via les méthodes statiques de Saisie
import java.util.Scanner;

/**
 * Vue console pour la gestion des commandes.
 * Utilise la classe utilitaire Saisie (méthodes statiques) au lieu de la classe Console,
 * pour valider les entrées clavier (nombres, textes non vides, etc.).
 *
 * IMPORTANT : Saisie doit être déclarée dans le même package ("view") que cette classe,
 * car ses méthodes sont package-private (sans modificateur d'accès).
 */
public class CommandeView {

    // Scanner unique partagé pour toutes les saisies de cette vue, passé à chaque appel de Saisie.xxx()
    private Scanner scanner = new Scanner(System.in);

    /**
     * Demande et retourne le numéro de la commande saisi par l'utilisateur.
     * Redemande tant que le texte saisi est vide.
     */
    public String saisirNumero() {
        return Saisie.lireTexteNonVide(scanner, "Numéro de la commande : ");
    }

    /**
     * Demande et retourne l'id d'une commande saisi par l'utilisateur.
     * Doit être compris entre 1 et Validateur.ID_MAX.
     */
    public int saisirId() {
        return lireEntierDansIntervalle(scanner, "Id de la commande : ", 1, Validateur.ID_MAX);
    }

    /**
     * Demande et retourne l'id du produit à ajouter à une commande.
     * Doit être compris entre 1 et Validateur.ID_MAX.
     */
    public int saisirIdProduit() {
        return lireEntierDansIntervalle(scanner, "Id du produit à ajouter : ", 1, Validateur.ID_MAX);
    }

    /**
     * Demande et retourne la quantité d'un produit à ajouter à une commande.
     * Doit être comprise entre Validateur.QUANTITE_MIN et Validateur.QUANTITE_MAX.
     */
    public int saisirQuantite() {
        return lireEntierDansIntervalle(scanner, "Quantité : ", Validateur.QUANTITE_MIN, Validateur.QUANTITE_MAX);
    }

    /**
     * Demande à l'utilisateur s'il souhaite ajouter un autre produit à la commande en cours.
     * Accepte uniquement "o" (oui, insensible à la casse) comme réponse positive.
     *
     * @return true si l'utilisateur a répondu "o", false sinon (y compris pour une saisie vide)
     */
    public boolean demanderAjoutAutreProduit() {
        // Ici on veut accepter une réponse vide ou "n" sans forcer une nouvelle saisie,
        // donc on lit directement la ligne au lieu d'utiliser lireTexteNonVide (qui redemanderait).
        System.out.print("Ajouter un autre produit ? (o/n) : ");
        String reponse = scanner.nextLine();
        return reponse.equalsIgnoreCase("o");
    }

    /**
     * Affiche un message quelconque à l'utilisateur.
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Affiche le détail d'une commande, ou un message d'erreur si elle est introuvable (null).
     */
    public void afficherCommande(Commande commande) {
        if (commande == null) {
            System.out.println("Commande introuvable.");
        } else {
            System.out.println(commande.toChaine());
        }
    }

    /**
     * Affiche la liste de toutes les commandes, ou un message si la liste est vide.
     */
    public void afficherCommandes(List<Commande> commandes) {
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande enregistrée.");
            return;
        }
        for (Commande commande : commandes) {
            System.out.println(commande.toChaine());
        }
    }

    /**
     * Affiche les lignes (produits + quantités) d'une commande donnée.
     */
    public void afficherLignes(Commande commande) {
        if (commande.getLignes().isEmpty()) {
            System.out.println("Aucun produit dans cette commande.");
            return;
        }
        commande.getLignes().forEach(ligne -> System.out.println(ligne.toChaine()));
    }

    /**
     * Méthode utilitaire privée : lit un entier au clavier et redemande tant que la valeur
     * n'est pas comprise dans l'intervalle [min, max]. S'appuie sur Saisie.lireEntier pour
     * la validation syntaxique (nombre bien formé), puis ajoute la validation de bornes
     * qui n'existe pas nativement dans Saisie.
     *
     * @param scanner le Scanner utilisé pour lire l'entrée clavier
     * @param message le message d'invite affiché à l'utilisateur
     * @param min      la valeur minimale acceptée (incluse)
     * @param max      la valeur maximale acceptée (incluse)
     * @return l'entier saisi, garanti compris entre min et max
     */
    private int lireEntierDansIntervalle(Scanner scanner, String message, int min, int max) {
        while (true) {
            int valeur = Saisie.lireEntier(scanner, message);
            if (valeur < min || valeur > max) {
                System.out.println("Valeur invalide, veuillez saisir un nombre entre " + min + " et " + max + ".");
                continue;
            }
            return valeur;
        }
    }
}