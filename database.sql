CREATE DATABASE IF NOT EXISTS gestion_commercialeAll;
USE gestion_commercialeAll;

-- 1. Table Client
CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20)
);

-- 2. Table Produit
CREATE TABLE produit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL,
    qtsock INT NOT NULL DEFAULT 0,
    prix_unitaire DOUBLE NOT NULL,
    statut_produit ENUM('disponible', 'rupture') NOT NULL
);

-- 3. Table Commande
CREATE TABLE commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(50) NOT NULL UNIQUE,
    date DATE NOT NULL,
    montant_total DOUBLE NOT NULL,
    validee BOOLEAN NOT NULL DEFAULT FALSE,
    id_client INT NOT NULL,
    FOREIGN KEY (id_client) REFERENCES client(id) ON DELETE CASCADE
);

-- 4. Table Ligne de Commande
CREATE TABLE ligne_commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantite INT NOT NULL,
    prix_s DOUBLE NOT NULL, -- Prix appliqué lors de la commande
    id_produit INT NOT NULL,
    id_commande INT NOT NULL,
    FOREIGN KEY (id_produit) REFERENCES produit(id),
    FOREIGN KEY (id_commande) REFERENCES commande(id) ON DELETE CASCADE
);

-- 5. Table Facture (Relation 1 à 1 avec Commande)
CREATE TABLE facture (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(50) NOT NULL UNIQUE,
    date DATE NOT NULL,
    montant DOUBLE NOT NULL,
    id_commande INT UNIQUE NOT NULL, -- UNIQUE garantit la relation 1 à 1
    FOREIGN KEY (id_commande) REFERENCES commande(id) ON DELETE CASCADE
);

-- 6. Table Paiement
CREATE TABLE paiement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(50) NOT NULL UNIQUE,
    montant_verse DOUBLE NOT NULL,
    date DATE NOT NULL,
    statut_paiement ENUM('partiellement_payee', 'totalement_payee', 'non payee') NOT NULL,
    id_facture INT NOT NULL,
    FOREIGN KEY (id_facture) REFERENCES facture(id) ON DELETE CASCADE
);
