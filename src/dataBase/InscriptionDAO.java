package dataBase;

import contracts.IInscription;
import models.Inscription;
import models.Etudiant;
import models.Groupe;
import services.EtudiantService;
import services.GroupeService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InscriptionDAO implements IInscription {

    private final Connection connection;
    private final EtudiantService etudiantService;
    private final GroupeService groupeService;

    public InscriptionDAO(Connection connection, EtudiantService etudiantService, GroupeService groupeService) {
        this.connection = connection;
        this.etudiantService = etudiantService;
        this.groupeService = groupeService;
    }

    @Override
    public List<Inscription> getAllInscriptions() throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscription";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                LocalDate dateInscription = resultSet.getDate("date_inscription").toLocalDate();
                int etudiantId = resultSet.getInt("id_etudiant");
                String groupeNom = resultSet.getString("nom_groupe");

                Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                Inscription inscription = new Inscription(dateInscription, etudiant, groupe);
                inscriptions.add(inscription);
            }
        }
        return inscriptions;
    }

    @Override
    public Inscription getInscriptionByEtudiantAndGroupe(int etudiantId, String groupeNom) throws SQLException {
        String query = "SELECT * FROM inscription WHERE id_etudiant = ? AND nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, etudiantId);
            preparedStatement.setString(2, groupeNom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    LocalDate dateInscription = resultSet.getDate("date_inscription").toLocalDate();
                    Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    return new Inscription(dateInscription, etudiant, groupe);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Inscription> getInscriptionsByEtudiantId(int etudiantId) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscription WHERE id_etudiant = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, etudiantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate dateInscription = resultSet.getDate("date_inscription").toLocalDate();
                    String groupeNom = resultSet.getString("nom_groupe");

                    Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Inscription inscription = new Inscription(dateInscription, etudiant, groupe);
                    inscriptions.add(inscription);
                }
            }
        }
        return inscriptions;
    }

    @Override
    public List<Inscription> getInscriptionsByGroupeNom(String groupeNom) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscription WHERE nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, groupeNom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate dateInscription = resultSet.getDate("date_inscription").toLocalDate();
                    int etudiantId = resultSet.getInt("id_etudiant");

                    Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Inscription inscription = new Inscription(dateInscription, etudiant, groupe);
                    inscriptions.add(inscription);
                }
            }
        }
        return inscriptions;
    }

    @Override
    public List<Inscription> getInscriptionsByDate(LocalDate date) throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscription WHERE date_inscription = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int etudiantId = resultSet.getInt("id_etudiant");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Inscription inscription = new Inscription(date, etudiant, groupe);
                    inscriptions.add(inscription);
                }
            }
        }
        return inscriptions;
    }

    @Override
    public List<Inscription> getInscriptionsByCriteria(Map<String, Object> criteria) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM inscription WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (criteria.containsKey("date_inscription")) {
            queryBuilder.append(" AND date_inscription = ?");
            parameters.add(Date.valueOf((LocalDate) criteria.get("date_inscription")));
        }
        if (criteria.containsKey("id_etudiant")) {
            queryBuilder.append(" AND id_etudiant = ?");
            parameters.add(criteria.get("id_etudiant"));
        }
        if (criteria.containsKey("nom_groupe")) {
            queryBuilder.append(" AND nom_groupe = ?");
            parameters.add(criteria.get("nom_groupe"));
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Inscription> inscriptions = new ArrayList<>();
                while (resultSet.next()) {
                    LocalDate dateInscription = resultSet.getDate("date_inscription").toLocalDate();
                    int etudiantId = resultSet.getInt("id_etudiant");
                    String groupeNom = resultSet.getString("nom_groupe");

                    Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);
                    Groupe groupe = groupeService.getGroupeByNom(groupeNom);

                    Inscription inscription = new Inscription(dateInscription, etudiant, groupe);
                    inscriptions.add(inscription);
                }
                return inscriptions;
            }
        }
    }

    @Override
    public void saveInscription(Inscription inscription) throws SQLException {
        String query = "INSERT INTO inscription (id_etudiant, nom_groupe, date_inscription) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, inscription.getEtudiant().getId());
            preparedStatement.setString(2, inscription.getGroupe().getNom_groupe());
            preparedStatement.setDate(3, Date.valueOf(inscription.getDateInscription()));

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateInscription(Inscription inscription) throws SQLException {
        String query = "UPDATE inscription SET date_inscription = ? WHERE id_etudiant = ? AND nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(inscription.getDateInscription()));
            preparedStatement.setInt(2, inscription.getEtudiant().getId());
            preparedStatement.setString(3, inscription.getGroupe().getNom_groupe());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteInscription(int etudiantId, String groupeNom) throws SQLException {
        String query = "DELETE FROM inscription WHERE id_etudiant = ? AND nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, etudiantId);
            preparedStatement.setString(2, groupeNom);

            preparedStatement.executeUpdate();
        }
    }
}
