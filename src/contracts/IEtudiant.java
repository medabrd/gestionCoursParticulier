package contracts;
import models.Etudiant;

import java.sql.SQLException;
import java.util.List;

public interface IEtudiant {
    // Get all students
    List<Etudiant> getAllEtudiants() throws SQLException;

    // Get student by ID
    Etudiant getEtudiantById(int id) throws SQLException;

    // Get student by email
    Etudiant getEtudiantByMail(String mail) throws  SQLException;

    // Save a new student
    void saveEtudiant(Etudiant etudiant) throws SQLException;

    // Update an existing student
    void updateEtudiant(Etudiant etudiant) throws SQLException;

    // Delete a student by ID
    void deleteEtudiant(int id) throws SQLException;

    void deleteEtudiant(String mail) throws SQLException;
}
