import dataBase.DataBaseConnection;
import dataBase.EtudiantDAO;
import models.Etudiant;
import services.EtudiantService;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {


       Connection connection =null ;

        try {

            // creation etudiant
            Etudiant newEtudiant = new Etudiant("@xfmial","ala","salah");
            //cration conn
            connection = DataBaseConnection.getConnection();
            // instance DAO et service
            EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
            EtudiantService etudiantService = new EtudiantService(etudiantDAO);

            // ajout
            etudiantService.saveEtudiant(newEtudiant);
            System.out.println("New student created successfully using EtudiantService!");


        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        } finally {
            if (connection !=  null) {
                try {
                    connection.close();
                    System.out.println("Connection closed successfully.");
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }

    }
}
