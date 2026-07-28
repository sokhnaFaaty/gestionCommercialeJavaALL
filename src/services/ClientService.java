// Le service sert de passerelle entre la logique de l'application et la base de données (ClientRepository). 
// Il applique les règles métiers (comme vérifier si un numéro existe déjà avant d'ajouter).

package services;

import entities.Client;
import repositories.ClientRepository;
import java.util.List;

public class ClientService {
    private ClientRepository repository;

    // Constructeur sans paramètre - le service crée son propre repository
    public ClientService() {
         // Le service initialise son propre repository pour parler à la BDD
        this.repository = new ClientRepository();
    }

    // Gardez aussi l'autre constructeur pour les tests (optionnel)
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public void ajouterClient(Client client) {
        repository.save(client);
    }

    public List<Client> listerClients() {
        return repository.findAll();
    }

    public Client rechercherParTelephone(String telephone) {
        return repository.findByTelephone(telephone);
    }
}