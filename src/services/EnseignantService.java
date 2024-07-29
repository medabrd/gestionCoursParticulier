package services;

import dataBase.EnseignantDAO;
import models.Enseignant;
import java.sql.SQLException;
import java.util.List;

public class EnseignantService {

    private final EnseignantDAO enseignantDAO;

    public EnseignantService(EnseignantDAO enseignantDAO) {
        this.enseignantDAO = enseignantDAO;
    }

    public List<Enseignant> getAllEnseignants() throws SQLException {
        return enseignantDAO.getAllEnseignants();
    }

    public Enseignant getEnseignantById(int id) throws SQLException {
        return enseignantDAO.getEnseignantById(id);
    }

    public Enseignant getEnseignantByMail(String mail) throws SQLException {
        return enseignantDAO.getEnseignantByMail(mail);
    }

    public void saveEnseignant(Enseignant enseignant) throws SQLException {
        enseignantDAO.saveEnseignant(enseignant);
    }

    public void updateEnseignant(Enseignant enseignant) throws SQLException {
        enseignantDAO.updateEnseignant(enseignant);
    }

    public void deleteEnseignant(int id) throws SQLException {
        enseignantDAO.deleteEnseignant(id);
    }

    public void deleteEnseignant(String mail) throws SQLException {
        enseignantDAO.deleteEnseignant(mail);
    }
}
