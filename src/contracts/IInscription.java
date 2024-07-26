package contracts;

import models.Inscription;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IInscription {

    // Get all inscriptions
    List<Inscription> getAllInscriptions() throws SQLException;

    // Get inscription by student ID and group name
    Inscription getInscriptionByEtudiantAndGroupe(int etudiantId, String groupeNom) throws SQLException;

    // Get inscriptions by student ID
    List<Inscription> getInscriptionsByEtudiantId(int etudiantId) throws SQLException;

    // Get inscriptions by group name
    List<Inscription> getInscriptionsByGroupeNom(String groupeNom) throws SQLException;

    // Get inscriptions by date
    List<Inscription> getInscriptionsByDate(LocalDate date) throws SQLException;

    // Get inscriptions by criteria (flexible querying)
    List<Inscription> getInscriptionsByCriteria(Map<String, Object> criteria) throws SQLException;

    // Save a new inscription
    void saveInscription(Inscription inscription) throws SQLException;

    // Update an existing inscription
    void updateInscription(Inscription inscription) throws SQLException;

    // Delete an inscription by student ID and group name
    void deleteInscription(int etudiantId, String groupeNom) throws SQLException;
}
