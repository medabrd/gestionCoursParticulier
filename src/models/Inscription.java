package models;
import java.time.LocalDate;
import java.util.Objects;

public class Inscription {

    private LocalDate dateInscription;

    //relations
    private Etudiant etudiant;
    private Groupe groupe;

    public Inscription(LocalDate date_inscription, Etudiant etudiant, Groupe groupe) {
        this.dateInscription = date_inscription;
        this.etudiant = etudiant;
        this.groupe = groupe;
    }



    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return Objects.equals(etudiant, that.etudiant) && Objects.equals(groupe, that.groupe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etudiant, groupe);
    }

    @Override
    public String toString() {
        return "models.Inscription{" +
                "dateInscription=" + dateInscription +
                ", etudiant=" + etudiant +
                ", groupe=" + groupe +
                '}';
    }
}
