package dataBase;

import contracts.ISalle;
import models.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO implements ISalle {
    private final Connection connection;

    public SalleDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Salle> getAllSalles() throws SQLException {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salle";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int numSalle = resultSet.getInt("num_salle");
                int capacite = resultSet.getInt("capacite");

                Salle salle = new Salle(numSalle, capacite);
                salles.add(salle);
            }
        }
        return salles;
    }

    @Override
    public Salle getSalleById(int id) throws SQLException {
        String query = "SELECT * FROM salle WHERE num_salle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int capacite = resultSet.getInt("capacite");

                    return new Salle(id, capacite);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Salle> getSallesByCapacite(int cap) throws SQLException {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salle WHERE capacite <= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cap);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int numSalle = resultSet.getInt("num_salle");
                    int capacite = resultSet.getInt("capacite");

                    Salle salle = new Salle(numSalle, capacite);
                    salles.add(salle);
                }
            }
        }
        return salles;
    }

    @Override
    public void saveSalle(Salle salle) throws SQLException {
        String query = "INSERT INTO salle (capacite) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, salle.getCapacite());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateSalle(Salle salle) throws SQLException {
        String query = "UPDATE salle SET capacite = ? WHERE num_salle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, salle.getCapacite());
            preparedStatement.setInt(2, salle.getNumSalle());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteSalle(int id) throws SQLException {
        String query = "DELETE FROM salle WHERE num_salle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }
}
