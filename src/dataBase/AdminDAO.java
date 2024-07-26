package dataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import contracts.IAdmin;
import models.Admin;

public class AdminDAO implements IAdmin {

    private final Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admin";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id=resultSet.getInt("id");
                String email = resultSet.getString("email"); // Assuming a typo in the schema
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");

                Admin admin = new Admin(id,email, nom, prenom);
                admins.add(admin);
            }
        }
        return admins;
    }

    @Override
    public Admin getAdminById(int id) throws SQLException {
        String query = "SELECT * FROM admin WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");

                    return new Admin(id,email, nom, prenom);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Admin getAdminByMail(String mail) throws  SQLException{
        String query = "SELECT * FROM admin WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");

                    return new Admin(id,email, nom, prenom);
                } else {
                    return null;
                }
            }
        }

    }

    @Override
    public void saveAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admin (email, nom, prenom) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, admin.getEmail());
            preparedStatement.setString(2, admin.getNom());
            preparedStatement.setString(3, admin.getPrenom());

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void updateAdmin(Admin admin) throws SQLException {
        String query = "UPDATE admin SET email = ?, nom = ?, prenom = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, admin.getEmail());
            preparedStatement.setString(2, admin.getNom());
            preparedStatement.setString(3, admin.getPrenom());
            preparedStatement.setInt(4, admin.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAdmin(int id) throws SQLException {
        String query = "DELETE FROM admin WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAdmin(String mail) throws SQLException {
        String query = "DELETE FROM admin WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mail);
            preparedStatement.executeUpdate();
        }
    }
}
