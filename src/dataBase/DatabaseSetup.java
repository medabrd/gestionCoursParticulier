package dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";
        String databaseName = "gestionCoursParticuliers";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            // creation base
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(createDatabaseSQL);

            // choix base
            statement.executeUpdate("USE " + databaseName);

            // creation des tbles
            String creationTableEtudiantSQL = "CREATE TABLE IF NOT EXISTS etudiant ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "email VARCHAR(255) UNIQUE, "
                    + "nom VARCHAR(255) NOT NULL, "
                    + "prenom VARCHAR(255) NOT NULL, "
                    + "PRIMARY KEY (id))";
            statement.executeUpdate(creationTableEtudiantSQL);

            String creationTableEnseignantSQL = "CREATE TABLE IF NOT EXISTS enseignant ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "email VARCHAR(255) UNIQUE, "
                    + "nom VARCHAR(255) NOT NULL, "
                    + "prenom VARCHAR(255) NOT NULL, "
                    + "PRIMARY KEY (id))";
            statement.executeUpdate(creationTableEnseignantSQL);

            String creationTableAdminSQL = "CREATE TABLE IF NOT EXISTS admin ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "email VARCHAR(255) UNIQUE, "
                    + "nom VARCHAR(255) NOT NULL, "
                    + "prenom VARCHAR(255) NOT NULL, "
                    + "PRIMARY KEY (id))";
            statement.executeUpdate(creationTableAdminSQL);

            String creationTableMatiereSQL = "CREATE TABLE IF NOT EXISTS matiere ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "libelle VARCHAR(255) NOT NULL, "
                    + "niveau VARCHAR(255) NOT NULL, "
                    + "section VARCHAR(255) NOT NULL, "
                    + "PRIMARY KEY (id),"
                    + "UNIQUE (libelle, niveau, section))";
            statement.executeUpdate(creationTableMatiereSQL);

            String createTableSalleSQL = "CREATE TABLE IF NOT EXISTS salle ( " +
                    "  num_salle INT NOT NULL AUTO_INCREMENT, " +
                    "  capacite INT NOT NULL, " +
                    "  PRIMARY KEY (num_salle) " +
                    ");";
            statement.executeUpdate(createTableSalleSQL);


            String creationTableGroupeSQL = "CREATE TABLE IF NOT EXISTS groupe ( " +
                    "  id_matiere INT NOT NULL, " +
                    "  id_enseignant INT NOT NULL, " +
                    "  nb_max_etudiant INT NOT NULL, " +
                    "  nom_groupe VARCHAR(255) UNIQUE NOT NULL, " +
                    "  PRIMARY KEY (id_matiere, id_enseignant), " +
                    "  FOREIGN KEY (id_matiere) REFERENCES matiere(id), " +
                    "  FOREIGN KEY (id_enseignant) REFERENCES enseignant(id) " +
                    ");";
            statement.executeUpdate(creationTableGroupeSQL);


            String createTableInscriptionSQL = "CREATE TABLE IF NOT EXISTS inscription ( " +
                    "  id_etudiant INT NOT NULL, " +
                    "  nom_groupe VARCHAR(255) NOT NULL, " +
                    "  date_inscription DATE NOT NULL, " +
                    "  FOREIGN KEY (id_etudiant) REFERENCES etudiant(id), " +
                    "  FOREIGN KEY (nom_groupe) REFERENCES groupe(nom_groupe), " +
                    "  PRIMARY KEY (id_etudiant,nom_groupe) " +
                    ");";
            statement.executeUpdate(createTableInscriptionSQL);

            String createTableSeanceSQL = "CREATE TABLE IF NOT EXISTS seance ( " +
                    "  id INT NOT NULL AUTO_INCREMENT, " +
                    "  nom_groupe VARCHAR(255) NOT NULL, " +
                    "  id_salle INT NOT NULL, " +
                    "  date_seance DATE NOT NULL, " +
                    "  heure_debut TIME NOT NULL, " +
                    "  PRIMARY KEY (id), " +
                    "  FOREIGN KEY (nom_groupe) REFERENCES groupe(nom_groupe), " +
                    "  FOREIGN KEY (id_salle) REFERENCES salle(num_salle) " +
                    ");";
            statement.executeUpdate(createTableSeanceSQL);

            String createTableAbsenceSQL = "CREATE TABLE IF NOT EXISTS absence ( " +
                    "  id_etudiant INT NOT NULL, " +
                    "  id_seance INT NOT NULL,"+
                    "  motif VARCHAR(255), " +
                    "  PRIMARY KEY (id_etudiant,id_seance), " +
                    "  FOREIGN KEY (id_seance) REFERENCES seance(id) " +
                    ");";
            statement.executeUpdate(createTableAbsenceSQL);

            String createTablePaiementSQL = "CREATE TABLE IF NOT EXISTS paiement ( " +
                    "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "  id_etudiant INT NOT NULL, " +
                    "  date_paiement DATE NOT NULL, " +
                    "  montant DECIMAL(10,2) NOT NULL, " +
                    "  FOREIGN KEY (id_etudiant) REFERENCES etudiant(id) " +
                    ");";
            statement.executeUpdate(createTablePaiementSQL);

            System.out.println("Database and tables created successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }
}
