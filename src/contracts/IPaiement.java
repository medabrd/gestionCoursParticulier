package contracts;

import models.Paiement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IPaiement {

    // Get all paiements
    List<Paiement> getAllPaiements() throws SQLException;

    // Get paiement by ID
    Paiement getPaiementById(int id) throws SQLException;

    // Get paiements by student ID
    List<Paiement> getPaiementsByEtudiantId(int etudiantId) throws SQLException;

    // Get paiements by date
    List<Paiement> getPaiementsByDate(LocalDate date) throws SQLException;

    // Save a new paiement
    void savePaiement(Paiement paiement) throws SQLException;

    // Update an existing paiement
    void updatePaiement(Paiement paiement) throws SQLException;

    // Delete a paiement by ID
    void deletePaiement(int id) throws SQLException;
}
