package dataBase;

import contracts.IAbsence;
import models.Absence;
import models.Etudiant;
import models.Seance;
import services.EtudiantService;
import services.SeanceService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbsenceDAO implements IAbsence {

    private final Connection connection;
    private final EtudiantService etudiantService;
    private final SeanceService seanceService;

    public AbsenceDAO(Connection connection, EtudiantService etudiantService, SeanceService seanceService) {
        this.connection = connection;
        this.etudiantService = etudiantService;
        this.seanceService = seanceService;
    }

    @Override
    public void saveAbsence(Absence absence) throws SQLException {
        String query = "INSERT INTO absence (id_etudiant, id_seance, motif) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, absence.getEtudiant().getId());
            preparedStatement.setInt(2, absence.getSeance().getId());
            preparedStatement.setString(3, absence.getMotif());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateAbsence(Absence absence) throws SQLException {
        String query = "UPDATE absence SET motif = ? WHERE id_etudiant = ? AND id_seance = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, absence.getMotif());
            preparedStatement.setInt(2, absence.getEtudiant().getId());
            preparedStatement.setInt(3, absence.getSeance().getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAbsence(Absence absence) throws SQLException {
        String query = "DELETE FROM absence WHERE id_etudiant = ? AND id_seance = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, absence.getEtudiant().getId());
            preparedStatement.setInt(2, absence.getSeance().getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Absence getAbsenceByStudentAndSeance(int studentId, int seanceId) throws SQLException {
        String query = "SELECT * FROM absence WHERE id_etudiant = ? AND id_seance = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, seanceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String motif = resultSet.getString("motif");

                    Etudiant etudiant = etudiantService.getEtudiantById(studentId);
                    Seance seance = seanceService.findSeanceById(seanceId);

                    return new Absence(etudiant, seance, motif);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Absence> getAbsencesByStudent(int studentId) throws SQLException {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT * FROM absence WHERE id_etudiant = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int seanceId = resultSet.getInt("id_seance");
                    String motif = resultSet.getString("motif");

                    Seance seance = seanceService.findSeanceById(seanceId);

                    Absence absence = new Absence(etudiantService.getEtudiantById(studentId), seance, motif);
                    absences.add(absence);
                }
            }
        }
        return absences;
    }

    @Override
    public List<Absence> getAbsencesBySeance(int seanceId) throws SQLException {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT * FROM absence WHERE id_seance = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, seanceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id_etudiant");
                    String motif = resultSet.getString("motif");

                    Etudiant etudiant = etudiantService.getEtudiantById(studentId);

                    Absence absence = new Absence(etudiant, seanceService.findSeanceById(seanceId), motif);
                    absences.add(absence);
                }
            }
        }
        return absences;
    }

    @Override
    public List<Absence> getAllAbsences() throws SQLException {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT * FROM absence";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id_etudiant");
                int seanceId = resultSet.getInt("id_seance");
                String motif = resultSet.getString("motif");

                Etudiant etudiant = etudiantService.getEtudiantById(studentId);
                Seance seance = seanceService.findSeanceById(seanceId);

                Absence absence = new Absence(etudiant, seance, motif);
                absences.add(absence);
            }
        }
        return absences;
    }

    @Override
    public List<Absence> getAbsencesByCriteria(Map<String, Object> criteria) throws SQLException {
        List<Absence> absences = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM absence WHERE 1=1");

        if (criteria.containsKey("id_etudiant")) {
            queryBuilder.append(" AND id_etudiant = ?");
        }
        if (criteria.containsKey("id_seance")) {
            queryBuilder.append(" AND id_seance = ?");
        }
        if (criteria.containsKey("motif")) {
            queryBuilder.append(" AND motif = ?");
        }

        String query = queryBuilder.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int index = 1;
            if (criteria.containsKey("id_etudiant")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id_etudiant"));
            }
            if (criteria.containsKey("id_seance")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id_seance"));
            }
            if (criteria.containsKey("motif")) {
                preparedStatement.setString(index++, (String) criteria.get("motif"));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id_etudiant");
                    int seanceId = resultSet.getInt("id_seance");
                    String motif = resultSet.getString("motif");

                    Etudiant etudiant = etudiantService.getEtudiantById(studentId);
                    Seance seance = seanceService.findSeanceById(seanceId);

                    Absence absence = new Absence(etudiant, seance, motif);
                    absences.add(absence);
                }
            }
        }
        return absences;
    }
}
