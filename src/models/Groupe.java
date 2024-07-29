package models;
import java.util.ArrayList;
import java.util.Objects;

public class Groupe {
    private static int numInstance=0; //variable de classe utilisé lors de la generation du nom de groupe afin de garenti
    //un nom unique
   private int nbMaxEtudiant;
   private String nom_groupe;

   //relations
   private Matiere matiere;
   private Enseignant enseignant;
   private ArrayList<Inscription> inscriptions=new ArrayList<>();
   private ArrayList<Seance> seances=new ArrayList<>();




    public Groupe(int nbMaxEtudiant,Enseignant enseignant, Matiere matiere) {
        numInstance++;
        this.nbMaxEtudiant = nbMaxEtudiant;
        this.nom_groupe = generateUniqueName(matiere)+numInstance;
        this.enseignant = enseignant;
        this.matiere = matiere;

    }

    public int getNbMaxEtudiant() {
        return nbMaxEtudiant;
    }

    public void setNbMaxEtudiant(int nbMaxEtudiant) {
        this.nbMaxEtudiant = nbMaxEtudiant;
    }



    public String getNom_groupe() {
        return nom_groupe;
    }

    public void setNom_groupe(String nom_groupe) {
        this.nom_groupe = nom_groupe;
    }



    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }



    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }



    public ArrayList<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(ArrayList<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }



    public void addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
    }

    public void removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
    }



    public ArrayList<Seance> getSeances() {
        return seances;
    }

    public void setSeances(ArrayList<Seance> seances) {
        this.seances = seances;
    }



    public void addSeance(Seance seance) {
        this.seances.add(seance);
    }

    public void removeSeance(Seance seance) {
        this.seances.remove(seance);
    }


    public static String generateUniqueName(Matiere matiere) {

        String matiereLibelle = matiere.getLibelle().substring(0, 3).toUpperCase();
        String matiereNiveau = matiere.getNiveau();
        String matiereSection = matiere.getSection();
        return "_" + matiereLibelle + "_" + matiereNiveau + "_"+matiereSection;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupe groupe = (Groupe) o;
        return Objects.equals(nom_groupe, groupe.nom_groupe) && Objects.equals(matiere, groupe.matiere) && Objects.equals(enseignant, groupe.enseignant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom_groupe, matiere, enseignant);
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "nom_groupe=" + nom_groupe +
                ", nbMaxEtudiant=" + nbMaxEtudiant +
                '}';
    }
}
