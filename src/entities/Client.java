package entities;

/**
 * Représente un client du magasin.
 * L'id est généré par la base de données (AUTO_INCREMENT), pas par le code Java.
 */
public class Client {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;

    // Constructeur utilisé quand le client vient d'être créé (pas encore d'id en base)
    public Client(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // Constructeur utilisé quand le client est relu depuis la base de données
    public Client(int id, String nom, String prenom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String toChaine() {
        return "==========================Client==============================" + "\n"
                + "  Id       : " + id + "\n"
                + "  Nom      : " + nom + "\n"
                + "  Prénom   : " + prenom + "\n"
                + "  Téléphone: " + telephone + "\n"
                + "==============================================================";
    }
}
