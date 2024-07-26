package services;
import dataBase.InscriptionDAO;
import models.Inscription;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InscriptionService {

    private final InscriptionDAO inscriptionDAO;

    public InscriptionService( InscriptionDAO inscriptionDAO) {
        this.inscriptionDAO = inscriptionDAO;
    }

    public List<Inscription> getAllInscriptions() {
        try {
            return inscriptionDAO.getAllInscriptions();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscriptions", e);
        }
    }

    public Inscription getInscriptionByEtudiantAndGroupe(int etudiantId, String groupeNom) {
        try {
            return inscriptionDAO.getInscriptionByEtudiantAndGroupe(etudiantId, groupeNom);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscription", e);
        }
    }

    public List<Inscription> getInscriptionsByEtudiantId(int etudiantId) {
        try {
            return inscriptionDAO.getInscriptionsByEtudiantId(etudiantId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscriptions for student", e);
        }
    }

    public List<Inscription> getInscriptionsByGroupeNom(String groupeNom) {
        try {
            return inscriptionDAO.getInscriptionsByGroupeNom(groupeNom);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscriptions for group", e);
        }
    }

    public List<Inscription> getInscriptionsByDate(LocalDate date) {
        try {
            return inscriptionDAO.getInscriptionsByDate(date);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscriptions by date", e);
        }
    }

    public List<Inscription> getInscriptionsByCriteria(Map<String, Object> criteria) {
        try {
            return inscriptionDAO.getInscriptionsByCriteria(criteria);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve inscriptions by criteria", e);
        }
    }

    public void saveInscription(Inscription inscription) {
        try {
            inscriptionDAO.saveInscription(inscription);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save inscription", e);
        }
    }

    public void updateInscription(Inscription inscription) {
        try {
            inscriptionDAO.updateInscription(inscription);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update inscription", e);
        }
    }

    public void deleteInscription(int etudiantId, String groupeNom) {
        try {
            inscriptionDAO.deleteInscription(etudiantId, groupeNom);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete inscription", e);
        }
    }
}
