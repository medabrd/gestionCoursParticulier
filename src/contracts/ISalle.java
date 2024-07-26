package contracts;
import models.Salle;
import java.sql.SQLException;
import java.util.List;

public interface ISalle{

    List<Salle> getAllSalles() throws SQLException;


    Salle getSalleById(int id) throws SQLException;

    List <Salle> getSallesByCapacite (int cap) throws SQLException;

    void saveSalle(Salle salle) throws SQLException;


    void updateSalle(Salle salle ) throws SQLException;


    void deleteSalle(int id) throws SQLException;
}
