package dataBase;
import contracts.IMatiere;
import models.Matiere;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatiereDAO implements IMatiere {
    private final Connection connection;

    public MatiereDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Matiere> getAllMatieres() throws SQLException {
        List<Matiere> matieres = new ArrayList<>();
        String query = "SELECT * FROM matiere";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String libelle = resultSet.getString("libelle");
                String niveau = resultSet.getString("niveau");
                String section = resultSet.getString("section");

                Matiere matiere = new Matiere(id, libelle, niveau, section);
                matieres.add(matiere);
            }
        }
        return matieres;
    }

    @Override
    public Matiere getMatiereById(int id) throws SQLException {
        String query = "SELECT * FROM matiere WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String libelle = resultSet.getString("libelle");
                    String niveau = resultSet.getString("niveau");
                    String section = resultSet.getString("section");

                    return new Matiere(id, libelle, niveau, section);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Matiere> getMatieresByCriteria(Map<String, Object> criteria) throws SQLException {
        List<Matiere> matieres = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM matiere WHERE 1=1");

        if (criteria.containsKey("id")) {
            query.append(" AND id = ?");
        }
        if (criteria.containsKey("libelle")) {
            query.append(" AND libelle = ?");
        }
        if (criteria.containsKey("niveau")) {
            query.append(" AND niveau = ?");
        }
        if (criteria.containsKey("section")) {
            query.append(" AND section = ?");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (criteria.containsKey("id")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id"));
            }
            if (criteria.containsKey("libelle")) {
                preparedStatement.setString(index++, (String) criteria.get("libelle"));
            }
            if (criteria.containsKey("niveau")) {
                preparedStatement.setString(index++, (String) criteria.get("niveau"));
            }
            if (criteria.containsKey("section")) {
                preparedStatement.setString(index++, (String) criteria.get("section"));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String libelle = resultSet.getString("libelle");
                    String niveau = resultSet.getString("niveau");
                    String section = resultSet.getString("section");

                    Matiere matiere = new Matiere(id, libelle, niveau, section);
                    matieres.add(matiere);
                }
            }
        }
        return matieres;
    }

    @Override
    public void saveMatiere(Matiere matiere) throws SQLException {
        String query = "INSERT INTO matiere (libelle, niveau, section) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, matiere.getLibelle());
            preparedStatement.setString(2, matiere.getNiveau());
            preparedStatement.setString(3, matiere.getSection());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateMatiere(Matiere matiere) throws SQLException {
        String query = "UPDATE matiere SET libelle = ?, niveau = ?, section = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, matiere.getLibelle());
            preparedStatement.setString(2, matiere.getNiveau());
            preparedStatement.setString(3, matiere.getSection());
            preparedStatement.setInt(4, matiere.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteMatiere(int id) throws SQLException {
        String query = "DELETE FROM matiere WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }
}
