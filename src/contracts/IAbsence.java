package contracts;
import models.Absence;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAbsence {

    // CRUD operations
    void saveAbsence(Absence absence) throws SQLException;
    void updateAbsence(Absence absence) throws SQLException;
    void deleteAbsence(Absence absence) throws SQLException;

    // Read operations
    Absence getAbsenceByStudentAndSeance(int studentId, int seanceId) throws SQLException;
    List<Absence> getAbsencesByStudent(int studentId) throws SQLException;
    List<Absence> getAbsencesBySeance(int seanceId) throws SQLException;
    List<Absence> getAllAbsences() throws SQLException;
    List<Absence> getAbsencesByCriteria(Map<String, Object> criteria) throws SQLException;
}
