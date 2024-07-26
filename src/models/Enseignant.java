package models;

import java.util.ArrayList;

public class Enseignant extends Utilisateur{
    //relations
    private ArrayList<Groupe> groupes=new ArrayList<>();;

    //Constructuer
    public Enseignant(String mail,String nom,String prenom){
        super(mail,nom,prenom,TypeUtilisateur.enseignant);
    }


    public Enseignant(int id,String email, String nom, String prenom) {
        super(email, nom, prenom,TypeUtilisateur.etudiant);
        this.setId(id);
    }


    // Getter groupes
    public ArrayList<Groupe> getGroupes() {
        return groupes;
    }

    // Setter groupes
    public void setGroupes(ArrayList<Groupe> groupes) {
        this.groupes = groupes;
    }

    // Ajout Groupe
    public void addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
    }

    // Suppression groupe
    public void removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
    }





}
