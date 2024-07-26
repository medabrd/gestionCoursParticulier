package services;

import dataBase.MatiereDAO;
import models.Matiere;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MatiereService {

    private final MatiereDAO matiereDAO;

    public MatiereService(MatiereDAO matiereDAO) {
        this.matiereDAO = matiereDAO;
    }

    public List<Matiere> getAllMatieres() throws SQLException {
        return matiereDAO.getAllMatieres();
    }

    public Matiere getMatiereById(int id) throws SQLException {
        return matiereDAO.getMatiereById(id);
    }

    public List<Matiere> getMatieresByCriteria(Map<String, Object> criteria) throws SQLException {
        return matiereDAO.getMatieresByCriteria(criteria);
    }

    public void saveMatiere(Matiere matiere) throws SQLException {
        matiereDAO.saveMatiere(matiere);
    }

    public void updateMatiere(Matiere matiere) throws SQLException {
        matiereDAO.updateMatiere(matiere);
    }

    public void deleteMatiere(int id) throws SQLException {
        matiereDAO.deleteMatiere(id);
    }
}
