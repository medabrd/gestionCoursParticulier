package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;


public class Seance {

    private int id;
    private LocalDate dateSeance;
    private LocalTime heureDebut;
    //relations
    private Salle salle ;
    private Groupe groupe ;
    private ArrayList<Absence> absences;


    public Seance(LocalDate date_seance, LocalTime heure_debut, Salle salle, Groupe groupe) {
        this.dateSeance = date_seance;
        this.heureDebut = heure_debut;
        this.salle = salle;
        this.groupe = groupe;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public ArrayList<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(ArrayList<Absence> absences) {
        this.absences = absences;
    }

    public void addAbsence(Absence absence) {
            this.absences.add(absence);
        }

    public void removeAbsence(Absence absence) {
          this.absences.remove(absence);
        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return Objects.equals(dateSeance, seance.dateSeance) && Objects.equals(heureDebut, seance.heureDebut) && Objects.equals(salle, seance.salle) && Objects.equals(groupe, seance.groupe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateSeance, heureDebut, salle, groupe);
    }

    @Override
    public String toString() {
        return "models.Seance{" +
                "dateSeance=" + dateSeance +
                ", heureDebut=" + heureDebut +
                ", salle=" + salle +
                ", groupe=" + groupe +
                '}';
    }
}
