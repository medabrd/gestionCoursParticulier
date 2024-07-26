package dataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import contracts.IEnseignant;
import models.Enseignant;

public class EnseignantDAO implements IEnseignant {

    private final Connection connection;

    public EnseignantDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Enseignant> getAllEnseignants() throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String query = "SELECT * FROM enseignant";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");

                Enseignant enseignant = new Enseignant(id, email, nom, prenom);
                enseignants.add(enseignant);
            }
        }
        return enseignants;
    }

    @Override
    public Enseignant getEnseignantById(int id) throws SQLException {
        String query = "SELECT * FROM enseignant WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");

                    return new Enseignant(id, email, nom, prenom);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Enseignant getEnseignantByMail(String mail) throws SQLException {
        String query = "SELECT * FROM enseignant WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");

                    return new Enseignant(id, email, nom, prenom);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void saveEnseignant(Enseignant enseignant) throws SQLException {
        String query = "INSERT INTO enseignant (email, nom, prenom) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, enseignant.getEmail());
            preparedStatement.setString(2, enseignant.getNom());
            preparedStatement.setString(3, enseignant.getPrenom());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateEnseignant(Enseignant enseignant) throws SQLException {
        String query = "UPDATE enseignant SET email = ?, nom = ?, prenom = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, enseignant.getEmail());
            preparedStatement.setString(2, enseignant.getNom());
            preparedStatement.setString(3, enseignant.getPrenom());
            preparedStatement.setInt(4, enseignant.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteEnseignant(int id) throws SQLException {
        String query = "DELETE FROM enseignant WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteEnseignant(String mail) throws SQLException {
        String query = "DELETE FROM enseignant WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mail);

            preparedStatement.executeUpdate();
        }
    }
}
