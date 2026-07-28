package services;

import java.util.List;

public class CategorieService {
    private CategorieRepository repository;

    public CategorieService() {
        this.repository = new CategorieRepository();
    }

    public void ajouterCategorie(Categorie categorie) {
        repository.save(categorie);
    }

    public List<Categorie> listerCategories() {
        return repository.findAll();
    }

    public Categorie rechercherParId(int id) {
        return repository.findById(id);
    }
}
