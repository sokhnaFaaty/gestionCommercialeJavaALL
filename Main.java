import java.util.Scanner;
import services.*;
import views.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Initialisation des Services
        ClientService clientService = new ClientService();
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        PaiementService paiementService = new PaiementService();
        FactureService factureService = new FactureService();

        // 2. Initialisation des Vues disponibles (Une seule fois ici)
        ClientView clientView = new ClientView(clientService, scanner);
        CategorieView categorieView = new CategorieView(categorieService, scanner);
        ProduitView produitView = new ProduitView(produitService, scanner);
        PaiementView paiementView = new PaiementView(paiementService, factureService, scanner);
        FactureView factureView = new FactureView(factureService, scanner); 

        // 3. Boucle du Menu Principal
        int choix;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gérer les Clients");
            System.out.println("2. Gérer les Catégories");
            System.out.println("3. Gérer les Produits");
            System.out.println("4. Gérer les Paiements");
            System.out.println("5. Gérer les Factures");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            
            choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    clientView.afficherMenu(); 
                    break;
                case 2:
                    categorieView.afficherMenu(); 
                    break;
                case 3:
                    produitView.afficherMenu();
                    break;
                case 4:
                    paiementView.afficherMenu();
                    break;
                case 5:
                    factureView.afficherMenu();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 0);

        scanner.close();
    }
}
