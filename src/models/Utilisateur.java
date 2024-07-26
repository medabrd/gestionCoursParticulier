package models;
import java.util.Objects;

public class Utilisateur {
    private int id ;
    private String email ;
    private String nom ;
    private String prenom ;
    private final TypeUtilisateur role ;

    //mot de passe ??


    Utilisateur (String email,String nom,String prenom,TypeUtilisateur role){
        //excluded set id cause it is handled automatically by the database system
        this.email=email;
        this.nom=nom;
        this.prenom=prenom;
        this.role=role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    //public TypeUtilisateur getRole() {
    //    return role;
    //}

    //public void setRole(TypeUtilisateur role) {
    //    this.role = role;
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nom, prenom, role);
    }

    @Override
    public String toString() {
        return "models.Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role=" + role +
                '}';
    }
}
