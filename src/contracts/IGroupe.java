package contracts;

import models.Groupe;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGroupe {

    // Get all groupes
    List<Groupe> getAllGroupes() throws SQLException;

    // Get groupe by unique name
    Groupe getGroupeByNom(String nomGroupe) throws SQLException;

    // Get groupes by matiere ID and enseignant ID
    List<Groupe> getGroupesByMatiereAndEnseignant(int idMatiere, int idEnseignant) throws SQLException;

    // Get groupes by criteria
    List<Groupe> getGroupesByCriteria(Map<String, Object> criteria) throws SQLException;

    // Get groupes by matiere ID
    List<Groupe> getGroupesByMatiere(int idMatiere) throws SQLException;

    // Get groupes by enseignant ID
    List<Groupe> getGroupesByEnseignant(int idEnseignant) throws SQLException;

    // Save a new groupe
    void saveGroupe(Groupe groupe) throws SQLException;

    // Update an existing groupe
    void updateGroupe(Groupe groupe) throws SQLException;

    // Delete a groupe by unique name
    void deleteGroupe(String nomGroupe) throws SQLException;
}
