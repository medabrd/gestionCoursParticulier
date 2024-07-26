package services;
import dataBase.GroupeDAO;
import models.Groupe;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GroupeService {

    private final GroupeDAO groupeDAO;

    public GroupeService(GroupeDAO groupeDAO) {
        this.groupeDAO = groupeDAO;
    }

    public List<Groupe> getAllGroupes() {
        try {
            return groupeDAO.getAllGroupes();
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching all groupes", e);
        }
    }

    public Groupe getGroupeByNom(String nomGroupe) {
        try {
            return groupeDAO.getGroupeByNom(nomGroupe);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching groupe by name", e);
        }
    }

    public List<Groupe> getGroupesByMatiereAndEnseignant(int idMatiere, int idEnseignant) {
        try {
            return groupeDAO.getGroupesByMatiereAndEnseignant(idMatiere, idEnseignant);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching groupes by matiere and enseignant", e);
        }
    }

    public List<Groupe> getGroupesByCriteria(Map<String, Object> criteria) {
        try {
            return groupeDAO.getGroupesByCriteria(criteria);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching groupes by criteria", e);
        }
    }

    public List<Groupe> getGroupesByMatiere(int idMatiere) {
        try {
            return groupeDAO.getGroupesByMatiere(idMatiere);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching groupes by matiere", e);
        }
    }

    public List<Groupe> getGroupesByEnseignant(int idEnseignant) {
        try {
            return groupeDAO.getGroupesByEnseignant(idEnseignant);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error fetching groupes by enseignant", e);
        }
    }

    public void saveGroupe(Groupe groupe) {
        try {
            groupeDAO.saveGroupe(groupe);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error saving groupe", e);
        }
    }

    public void updateGroupe(Groupe groupe) {
        try {
            groupeDAO.updateGroupe(groupe);
        } catch (SQLException e) {
            // Handle the exception, e.g., log it or rethrow it as a runtime exception
            throw new RuntimeException("Error updating groupe", e);
        }
    }

    public void deleteGroupe(String nomGroupe) {
        try {
            groupeDAO.deleteGroupe(nomGroupe);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting groupe", e);
        }
    }


}
