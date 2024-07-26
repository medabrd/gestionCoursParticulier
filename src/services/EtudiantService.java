package services;
import dataBase.EtudiantDAO;
import models.Etudiant;
import java.sql.SQLException;
import java.util.List;

public class EtudiantService {

    private final EtudiantDAO etudiantDAO;

    public EtudiantService(EtudiantDAO etudiantDAO) {
        this.etudiantDAO = etudiantDAO;
    }

    public List<Etudiant> getAllEtudiants() throws SQLException {
        return etudiantDAO.getAllEtudiants();
    }

    public Etudiant getEtudiantById(int id) throws SQLException {
        return etudiantDAO.getEtudiantById(id);
    }

    public Etudiant getEtudiantByMail(String mail) throws SQLException {
        return etudiantDAO.getEtudiantByMail(mail);
    }

    public void saveEtudiant(Etudiant etudiant) throws SQLException {
        etudiantDAO.saveEtudiant(etudiant);
    }

    public void updateEtudiant(Etudiant etudiant) throws SQLException {
        etudiantDAO.updateEtudiant(etudiant);
    }

    public void deleteEtudiant(int id) throws SQLException {
        etudiantDAO.deleteEtudiant(id);
    }

    public void deleteEtudiant(String mail) throws SQLException {
        etudiantDAO.deleteEtudiant(mail);
    }
}
