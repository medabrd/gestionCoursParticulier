package models;

import java.util.ArrayList;
import java.util.Objects;

public class Matiere {
    private int id ;
    private String libelle;
    private String niveau;
    private String section;

    //relations
    private ArrayList<Groupe> groupes=new ArrayList<>();
    //

    public Matiere(int id,String libelle,String niveau,String section){
        this.id=id;
        this.libelle=libelle;
        this.niveau=niveau;
        this.section=section;
    }
    public Matiere(String libelle,String niveau,String section){

        this.libelle=libelle;
        this.niveau=niveau;
        this.section=section;
    }

    public int getId(){
        return id ;
    }



    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLibelle(){
        return libelle;
    }

    public void setLibelle(String libelle){
        this.libelle=libelle;
    }

    public ArrayList<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(ArrayList<Groupe> groupes) {
        this.groupes = groupes;
    }

    public void addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
    }

    public void removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
    }

    @Override
    public String toString() {
        return "models.Matiere{" +
                "id=" + id +
                ", libelle='" + libelle +
                ", niveau='" + niveau +
                ", section='" + section +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matiere matiere = (Matiere) o;
        return id == matiere.id && Objects.equals(libelle, matiere.libelle) && Objects.equals(niveau, matiere.niveau) && Objects.equals(section, matiere.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, niveau, section);
    }
}
