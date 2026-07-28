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

public class ClientView {
    private Scanner scanner;

    public ClientView(Scanner scanner) {
        this.scanner = scanner;
    }

    // Le menu affiche juste les options et retourne le choix de l'utilisateur
    public int afficherMenu() {
        System.out.println("\n--- GESTION DES CLIENTS ---");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Lister les clients");
        System.out.println("3. Rechercher un client par téléphone (Bonus)");
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }

    // Cette méthode crée et retourne un objet Client sans appeler le service
    public Client saisirClient() {
        String nom = "";
        while (nom.trim().isEmpty()) {
            System.out.print("Nom (Obligatoire) : ");
            nom = scanner.nextLine();
        }

        String prenom = "";
        while (prenom.trim().isEmpty()) {
            System.out.print("Prénom (Obligatoire) : ");
            prenom = scanner.nextLine();
        }

        String tel = "";
        while (tel.trim().isEmpty()) {
            System.out.print("Téléphone (Obligatoire) : ");
            tel = scanner.nextLine();
        }

        return new Client(0, nom, prenom, tel);
    }

    // Reçoit la liste depuis le Main et l'affiche
    public void afficherClients(List<Client> clients) {
        System.out.println("\nListe des clients :");
        for (Client c : clients) {
            c.toChaine();
        }
    }

    // Demande le téléphone et le retourne
    public String saisirTelephone() {
        System.out.print("Entrez le numéro de téléphone à rechercher : ");
        return scanner.nextLine();
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
