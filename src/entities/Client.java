package entities;

import java.util.ArrayList;
import java.util.List;

public class Client {
   private int id;
   private String nom;
   private String prenom;
   private String telephone;
   private List<Commande> commandes;

   public Client() {
      this.commandes = new ArrayList();
   }

   public Client(int var1, String var2, String var3, String var4) {
      this.id = var1;
      this.nom = var2;
      this.prenom = var3;
      this.telephone = var4;
      this.commandes = new ArrayList();
   }

   public int getId() {
      return this.id;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public String getNom() {
      return this.nom;
   }

   public void setNom(String var1) {
      this.nom = var1;
   }

   public String getPrenom() {
      return this.prenom;
   }

   public void setPrenom(String var1) {
      this.prenom = var1;
   }

   public String getTelephone() {
      return this.telephone;
   }

   public void setTelephone(String var1) {
      this.telephone = var1;
   }

   public List<Commande> getCommandes() {
      return this.commandes;
   }

   public void addCommande(Commande var1) {
      this.commandes.add(var1);
   }

   public void toChaine() {
      System.out.println("id=" + this.id + ", nom=" + this.nom + ", prenom=" + this.prenom + ", telephone=" + this.telephone);
   }
}
