package services;
import dataBase.SeanceDAO;
import models.Seance;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class SeanceService {

    private final SeanceDAO seanceDAO;

    public SeanceService(SeanceDAO seanceDAO) {
        this.seanceDAO = seanceDAO;
    }

    // CRUD operations
    public void createSeance(Seance seance) {
        try {
            seanceDAO.saveSeance(seance);
        } catch (SQLException e) {
           throw new RuntimeException("Failed to create seance",e);
        }
    }

    public void updateSeance(Seance seance) {
        try {
            seanceDAO.updateSeance(seance);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update seance",e);
        }
    }

    public void removeSeance(int id) {
        try {
            seanceDAO.deleteSeance(id);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to remove seance",e);
        }
    }

    public Seance findSeanceById(int id) {
        try {
            return seanceDAO.getSeanceById(id);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find seance by ID",e);
        }
    }

    // Read operations
    public List<Seance> findAllSeances() {
        try {
            return seanceDAO.getAllSeances();
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find all seances",e);
        }
    }

    public List<Seance> findSeancesByDate(LocalDate date) {
        try {
            return seanceDAO.getSeancesByDate(date);
        } catch (SQLException e) {
            throw new RuntimeException ( "Failed to find seances by date",e);
        }
    }

    public List<Seance> findSeancesByDateTime(LocalDate date, LocalTime time) {
        try {
            return seanceDAO.getSeancesByDateTime(date, time);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find seances by date and time",e);
        }
    }

    public List<Seance> findSeancesByGroupe(String groupeNom) {
        try {
            return seanceDAO.getSeancesByGroupe(groupeNom);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find seances by group",e);
        }
    }

    public List<Seance> findSeancesBySalle(int salleId) {
        try {
            return seanceDAO.getSeancesBySalle(salleId);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find seances by salle",e);
        }
    }

    public List<Seance> findSeancesByCriteria(Map<String, Object> criteria) {
        try {
            return seanceDAO.getSeancesByCriteria(criteria);
        } catch (SQLException e) {
            throw new RuntimeException ("Failed to find seances by criteria",e);
        }
    }


}
