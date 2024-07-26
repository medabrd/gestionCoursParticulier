package models;

public class Admin extends Utilisateur{

    public Admin(int id, String email, String nom, String prenom) {
        super(email, nom, prenom, TypeUtilisateur.admin);
        this.setId(id);

    }

    public Admin(String email, String nom, String prenom) {
        super(email, nom, prenom,TypeUtilisateur.etudiant);
    }










}

