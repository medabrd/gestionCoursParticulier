package contracts;

import models.Seance;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface ISeance {


    void saveSeance(Seance seance) throws SQLException;
    void updateSeance(Seance seance) throws SQLException;
    void deleteSeance(int id) throws SQLException;
    Seance getSeanceById(int id) throws SQLException;
    List<Seance> getAllSeances() throws SQLException;
    List<Seance> getSeancesByDate(LocalDate date) throws SQLException;
    List<Seance> getSeancesByDateTime(LocalDate date, LocalTime time) throws SQLException;
    List<Seance> getSeancesByGroupe(String groupeNom) throws SQLException;
    List<Seance> getSeancesBySalle(int salleId) throws SQLException;
    List<Seance> getSeancesByCriteria(Map<String, Object> criteria) throws SQLException;
}
