package contracts;

import models.Matiere;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IMatiere {
    // Get all subjects
    List<Matiere> getAllMatieres() throws SQLException;

    // Get subject by ID
    Matiere getMatiereById(int id) throws SQLException;

    // Get subjects based on various criteria
    List<Matiere> getMatieresByCriteria(Map<String, Object> criteria) throws SQLException;

    // Save a new subject
    void saveMatiere(Matiere matiere) throws SQLException;

    // Update an existing subject
    void updateMatiere(Matiere matiere) throws SQLException;

    // Delete a subject by ID
    void deleteMatiere(int id) throws SQLException;
}
