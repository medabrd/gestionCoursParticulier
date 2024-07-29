package models;

import java.util.ArrayList;
import java.util.Objects;

public class Salle {
    private int numSalle ;
    private int capacite ;

    //relations
    private ArrayList<Seance> seances=new ArrayList<>();

    public Salle(int numSalle, int capacite) {
        this.numSalle = numSalle;
        this.capacite = capacite;
    }

    public int getNumSalle() {
        return numSalle;
    }

    public void setNumSalle(int numSalle) {
        this.numSalle = numSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return numSalle == salle.numSalle ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numSalle);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "numSalle=" + numSalle +
                ", capacite=" + capacite +
                '}';
    }
}
