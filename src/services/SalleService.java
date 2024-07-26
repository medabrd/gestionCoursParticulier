package services;
import dataBase.SalleDAO;
import models.Salle;
import java.sql.SQLException;
import java.util.List;

public class SalleService {

    private final SalleDAO salleDAO;

    public SalleService(SalleDAO salleDAO) {
        this.salleDAO = salleDAO;
    }

    // Get all salles
    public List<Salle> getAllSalles() throws SQLException {
        return salleDAO.getAllSalles();
    }

    // Get salle by ID
    public Salle getSalleById(int id) throws SQLException {
        return salleDAO.getSalleById(id);
    }

    // Get salles by capacite
    public List<Salle> getSallesByCapacite(int capacite) throws SQLException {
        return salleDAO.getSallesByCapacite(capacite);
    }

    // Save a new salle
    public void saveSalle(Salle salle) throws SQLException {
        salleDAO.saveSalle(salle);
    }

    // Update an existing salle
    public void updateSalle(Salle salle) throws SQLException {
        salleDAO.updateSalle(salle);
    }

    // Delete a salle by ID
    public void deleteSalle(int id) throws SQLException {
        salleDAO.deleteSalle(id);
    }
}
