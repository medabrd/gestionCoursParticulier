package services;
import contracts.IPaiement;
import dataBase.PaiementDAO;
import models.Paiement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaiementService {

    private final PaiementDAO paiementDAO;

    public PaiementService(PaiementDAO paiementDAO) {
        this.paiementDAO = paiementDAO;
    }

    public List<Paiement> getAllPaiements() {
        try {
            return paiementDAO.getAllPaiements();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving paiements", e);
        }
    }

    public Paiement getPaiementById(int id) {
        try {
            return paiementDAO.getPaiementById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving paiement by ID", e);
        }
    }

    public List<Paiement> getPaiementsByEtudiantId(int etudiantId) {
        try {
            return paiementDAO.getPaiementsByEtudiantId(etudiantId);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving paiements by etudiant ID", e);
        }
    }

    public List<Paiement> getPaiementsByDate(LocalDate date) {
        try {
            return paiementDAO.getPaiementsByDate(date);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving paiements by date", e);
        }
    }

    public void savePaiement(Paiement paiement) {
        try {
            paiementDAO.savePaiement(paiement);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving paiement", e);
        }
    }

    public void updatePaiement(Paiement paiement) {
        try {
            paiementDAO.updatePaiement(paiement);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating paiement", e);
        }
    }

    public void deletePaiement(int id) {
        try {
            paiementDAO.deletePaiement(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting paiement", e);
        }
    }
}
