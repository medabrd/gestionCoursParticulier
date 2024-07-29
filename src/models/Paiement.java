package models;

import java.time.LocalDate;
import java.util.Objects;

public class Paiement {


    private int id;
    private LocalDate date_paiement;
    private double montant;

    //relations
    private Etudiant etudiant;

    public Paiement( LocalDate date_paiement, double montant, Etudiant etudiant) {
        this.date_paiement = date_paiement;
        this.montant = montant;
        this.etudiant = etudiant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(LocalDate date_paiement) {
        this.date_paiement = date_paiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
        return id == paiement.id && Double.compare(montant, paiement.montant) == 0 && Objects.equals(date_paiement, paiement.date_paiement) && Objects.equals(etudiant, paiement.etudiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_paiement, montant, etudiant);
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", date_paiement=" + date_paiement +
                ", montant=" + montant +
                ", etudiant=" + etudiant +
                '}';
    }
}
