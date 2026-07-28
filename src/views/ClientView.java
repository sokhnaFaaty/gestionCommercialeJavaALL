// La vue doit uniquement s'occuper de saisir les données et de les renvoyer à la classe qui l'a appelée.

// L'Option A : La Vue n'est liée qu'à des objets de données simples (Client, String, List). 
// Elle est totalement indépendante du Service. 
// On peut modifier toute la logique métier dans notre application sans jamais ouvrir le fichier de la Vue.
// Le Main demande le choix à la vue (qui renvoie le nombre 2).
// Le Main va chercher lui-même la liste auprès du service (service.listerClients()).
// Le Main donne cette liste à la vue (view.afficherClients(liste)).
// La vue se contente d'afficher ce qu'on lui a donné, sans se poser de questions

package views;

import entities.Client;
import java.util.Scanner;
import java.util.List;
import views.Saisie;

public class ClientView {
    private Scanner scanner;

    public ClientView(Scanner scanner) {
        this.scanner = scanner;
    }

    // Le menu affiche juste les options et retourne le choix de l'utilisateur de manière sécurisée
    public int afficherMenu() {
        System.out.println("\n--- GESTION DES CLIENTS ---");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Lister les clients");
        System.out.println("3. Rechercher un client par téléphone (Bonus)");
        
        // Empêche le plantage si l'utilisateur saisit une lettre à la place d'un chiffre
        return Saisie.lireEntier(scanner, "Votre choix : ");
    }

    // Cette méthode crée et retourne un objet Client en appliquant vos règles de validation
    public Client saisirClient() {
        // Bloque les chiffres et les chaînes vides
        String nom = Saisie.lireTexteAlphabetique(scanner, "Nom (Obligatoire) : ");
        
        // Bloque les chiffres et les chaînes vides
        String prenom = Saisie.lireTexteAlphabetique(scanner, "Prénom (Obligatoire) : ");
        
        // Bloque les lettres et les chaînes vides (utilise la validation téléphone ajoutée à Saisie)
        String tel = Saisie.lireTelephoneValide(scanner, "Téléphone (Obligatoire) : ");

        return new Client(0, nom, prenom, tel);
    }

    // Reçoit la liste depuis le Main et l'affiche
    public void afficherClients(List<Client> clients) {
        System.out.println("\nListe des clients :");
        for (Client c : clients) {
            c.toChaine();
        }
    }

    // Demande le téléphone de manière sécurisée et le retourne
    public String saisirTelephone() {
        return Saisie.lireTelephoneValide(scanner, "Entrez le numéro de téléphone à rechercher : ");
    }

    // Reçoit le client trouvé (ou null) et l'affiche
    public void afficherResultatRecherche(Client c) {
        if (c != null) {
            c.toChaine();
        } else {
            System.out.println("Aucun client trouvé avec ce numéro.");
        }
    }
}

