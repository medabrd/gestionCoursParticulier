package models;

import java.util.Objects;

public class Absence {

    private Etudiant etudiant;//nonLinked to student (avoid cyclic dependency)
    private Seance seance;
    private String motif;

    public Absence(Etudiant etudiant, Seance seance) {
        this.etudiant = etudiant;
        this.seance = seance;
        this.motif = "pas de motif";
    }

    public Absence(Etudiant etudiant, Seance seance, String motif) {
        this.etudiant = etudiant;
        this.seance = seance;
        this.motif = motif;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }



    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }



    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(etudiant, absence.etudiant) && Objects.equals(seance, absence.seance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etudiant, seance);
    }

    @Override
    public String toString() {
        return "models.Absence{" +
                "etudiant=" + etudiant +
                ", seance=" + seance +
                ", motif='" + motif +
                '}';
    }
}
