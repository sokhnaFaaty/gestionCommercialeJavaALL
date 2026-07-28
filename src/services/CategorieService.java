package services;

import entities.Categorie;
import repositories.CategorieRepositories;
import repositories.CategorieRepositories;
import java.util.List;

public class CategorieService {
    private CategorieRepositories repositories;

    public CategorieService() {
        this.repositories = new CategorieRepositories();
    }

    public void ajouterCategorie(Categorie categorie) {
        repositories.save(categorie);
    }

    public List<Categorie> listerCategories() {
        return repositories.findAll();
    }

    public Categorie rechercherParId(int id) {
        return repositories.findById(id);
    }
}
