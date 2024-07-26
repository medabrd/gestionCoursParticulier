package services;

import contracts.IAbsence;
import dataBase.AbsenceDAO;
import models.Absence;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AbsenceService {

    private final AbsenceDAO absenceDAO;

    public AbsenceService(AbsenceDAO absenceDAO) {
        this.absenceDAO = absenceDAO;
    }

    public void saveAbsence(Absence absence) {
        try {
            absenceDAO.saveAbsence(absence);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save absence", e);
        }
    }

    public void updateAbsence(Absence absence) {
        try {
            absenceDAO.updateAbsence(absence);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update absence", e);
        }
    }

    public void deleteAbsence(Absence absence) {
        try {
            absenceDAO.deleteAbsence(absence);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete absence", e);
        }
    }

    public Absence getAbsenceByStudentAndSeance(int studentId, int seanceId) {
        try {
            return absenceDAO.getAbsenceByStudentAndSeance(studentId, seanceId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve absence", e);
        }
    }

    public List<Absence> getAbsencesByStudent(int studentId) {
        try {
            return absenceDAO.getAbsencesByStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve absences by student", e);
        }
    }

    public List<Absence> getAbsencesBySeance(int seanceId) {
        try {
            return absenceDAO.getAbsencesBySeance(seanceId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve absences by seance", e);
        }
    }

    public List<Absence> getAllAbsences() {
        try {
            return absenceDAO.getAllAbsences();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all absences", e);
        }
    }

    public List<Absence> getAbsencesByCriteria(Map<String, Object> criteria) {
        try {
            return absenceDAO.getAbsencesByCriteria(criteria);
        } catch (SQLException e) {

            throw new RuntimeException("Failed to retrieve absences by criteria", e);
        }
    }
}
