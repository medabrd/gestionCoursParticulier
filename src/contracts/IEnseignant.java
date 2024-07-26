package contracts;

import models.Enseignant;

import java.sql.SQLException;
import java.util.List;

public interface IEnseignant {
    // Get all teachers
    List<Enseignant> getAllEnseignants() throws SQLException;

    // Get teacher by ID
    Enseignant getEnseignantById(int id) throws SQLException;

    // Get teacher by email
    Enseignant getEnseignantByMail(String mail) throws SQLException;

    // Save a new teacher
    void saveEnseignant(Enseignant enseignant) throws SQLException;

    // Update an existing teacher
    void updateEnseignant(Enseignant enseignant) throws SQLException;

    // Delete a teacher by ID
    void deleteEnseignant(int id) throws SQLException;

    // Delete a teacher by email
    void deleteEnseignant(String mail) throws SQLException;
}
