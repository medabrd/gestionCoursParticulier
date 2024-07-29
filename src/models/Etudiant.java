package models;
import java.util.ArrayList;

public class Etudiant extends Utilisateur {

    // Relations
    private ArrayList<Inscription> inscriptions=new ArrayList<>();
    private ArrayList<Paiement> paiements=new ArrayList<>();;

   //Constructeur
    public Etudiant(String email, String nom, String prenom) {
        super(email, nom, prenom,TypeUtilisateur.etudiant);
    }

    public Etudiant(int id,String email, String nom, String prenom) {
        super(email, nom, prenom,TypeUtilisateur.etudiant);
        this.setId(id);
    }


    // Getter inscriptions
    public ArrayList<Inscription> getInscriptions() {
        return inscriptions;
    }

    // Setter  inscriptions
    public void setInscriptions(ArrayList<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    //  Ajout  inscription
    public void addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
    }

    // Suppression inscription
    public void removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
    }

    // Getter  payments
    public ArrayList<Paiement> getPaiements() {
        return paiements; // Return a copy to avoid modification of the original list
    }

    // Setter  payments
    public void setPaiements(ArrayList<Paiement> paiements) {
        this.paiements = paiements; // This might overwrite existing payments, be cautious
    }

    // Ajout payment
    public void addPaiement(Paiement paiement) {
        this.paiements.add(paiement);
    }

    // Suppression payment
    public void removePaiement(Paiement paiement) {
        this.paiements.remove(paiement);
    }

    @Override
    public String toString() {
        return
                "id=" + this.getId() +'\''+
                ", email='" + this.getEmail() + '\'' +
                ", nom='" + this.getNom()+ '\'' +
                ", prenom='" + this.getPrenom() + '\'';
    }
}
