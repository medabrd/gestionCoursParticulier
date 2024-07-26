package dataBase;
import contracts.ISeance;
import models.Seance;
import models.Salle;
import models.Groupe;
import services.GroupeService;
import services.SalleService;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeanceDAO implements ISeance {

    private final Connection connection;
    private final SalleService salleService;
    private final GroupeService groupeService;


    public SeanceDAO(Connection connection,SalleService salleService,GroupeService groupeService) {
        this.connection = connection;
        this.salleService=salleService;
        this.groupeService=groupeService;

    }

    @Override
    public void saveSeance(Seance seance) throws SQLException {
        String query = "INSERT INTO seance (nom_groupe, id_salle, date_seance, heure_debut) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, seance.getGroupe().getNom_groupe());
            preparedStatement.setInt(2, seance.getSalle().getNumSalle());
            preparedStatement.setDate(3, Date.valueOf(seance.getDateSeance()));
            preparedStatement.setTime(4, Time.valueOf(seance.getHeureDebut()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateSeance(Seance seance) throws SQLException {
        String query = "UPDATE seance SET nom_groupe = ?, id_salle = ?, date_seance = ?, heure_debut = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, seance.getGroupe().getNom_groupe());
            preparedStatement.setInt(2, seance.getSalle().getNumSalle());
            preparedStatement.setDate(3, Date.valueOf(seance.getDateSeance()));
            preparedStatement.setTime(4, Time.valueOf(seance.getHeureDebut()));
            preparedStatement.setInt(5, seance.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteSeance(int id) throws SQLException {
        String query = "DELETE FROM seance WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Seance getSeanceById(int id) throws SQLException {
        String query = "SELECT * FROM seance WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    LocalDate dateSeance = resultSet.getDate("date_seance").toLocalDate();
                    LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                    int salleId = resultSet.getInt("id_salle");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(dateSeance, heureDebut, salle, groupe);
                    seance.setId(id);
                    return seance;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Seance> getAllSeances() throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate dateSeance = resultSet.getDate("date_seance").toLocalDate();
                LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                int salleId = resultSet.getInt("id_salle");
                String groupeNom = resultSet.getString("nom_groupe");

                Salle salle = salleService.getSalleById(salleId);
                Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                Seance seance = new Seance(dateSeance, heureDebut, salle, groupe);
                seance.setId(id);
                seances.add(seance);
            }
        }
        return seances;
    }

    @Override
    public List<Seance> getSeancesByDate(LocalDate date) throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance WHERE date_seance = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                    int salleId = resultSet.getInt("id_salle");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(date, heureDebut, salle, groupe);
                    seance.setId(id);
                    seances.add(seance);
                }
            }
        }
        return seances;
    }

    @Override
    public List<Seance> getSeancesByDateTime(LocalDate date, LocalTime time) throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance WHERE date_seance = ? AND heure_debut = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int salleId = resultSet.getInt("id_salle");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(date, time, salle, groupe);
                    seance.setId(id);
                    seances.add(seance);
                }
            }
        }
        return seances;
    }

    @Override
    public List<Seance> getSeancesByGroupe(String groupeNom) throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance WHERE nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, groupeNom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDate dateSeance = resultSet.getDate("date_seance").toLocalDate();
                    LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                    int salleId = resultSet.getInt("id_salle");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(dateSeance, heureDebut, salle, groupe);
                    seance.setId(id);
                    seances.add(seance);
                }
            }
        }
        return seances;
    }

    @Override
    public List<Seance> getSeancesBySalle(int salleId) throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seance WHERE id_salle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, salleId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDate dateSeance = resultSet.getDate("date_seance").toLocalDate();
                    LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                    String groupeNom = resultSet.getString("nom_groupe");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(dateSeance, heureDebut, salle, groupe);
                    seance.setId(id);
                    seances.add(seance);
                }
            }
        }
        return seances;
    }

    @Override
    public List<Seance> getSeancesByCriteria(Map<String, Object> criteria) throws SQLException {
        List<Seance> seances = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM seance WHERE 1=1");

        if (criteria.containsKey("date_seance")) {
            queryBuilder.append(" AND date_seance = ?");
        }
        if (criteria.containsKey("heure_debut")) {
            queryBuilder.append(" AND heure_debut = ?");
        }
        if (criteria.containsKey("nom_groupe")) {
            queryBuilder.append(" AND nom_groupe = ?");
        }
        if (criteria.containsKey("id_salle")) {
            queryBuilder.append(" AND id_salle = ?");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            int index = 1;
            if (criteria.containsKey("date_seance")) {
                preparedStatement.setDate(index++, Date.valueOf((LocalDate) criteria.get("date_seance")));
            }
            if (criteria.containsKey("heure_debut")) {
                preparedStatement.setTime(index++, Time.valueOf((LocalTime) criteria.get("heure_debut")));
            }
            if (criteria.containsKey("nom_groupe")) {
                preparedStatement.setString(index++, (String) criteria.get("nom_groupe"));
            }
            if (criteria.containsKey("id_salle")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id_salle"));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDate dateSeance = resultSet.getDate("date_seance").toLocalDate();
                    LocalTime heureDebut = resultSet.getTime("heure_debut").toLocalTime();
                    int salleId = resultSet.getInt("id_salle");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Salle salle = salleService.getSalleById(salleId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Seance seance = new Seance(dateSeance, heureDebut, salle, groupe);
                    seance.setId(id);
                    seances.add(seance);
                }
            }
        }
        return seances;
    }
}
