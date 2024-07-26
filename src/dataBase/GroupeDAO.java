package dataBase;
import contracts.IGroupe;
import models.Groupe;
import models.Enseignant;
import models.Matiere;
import services.EnseignantService;
import services.MatiereService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupeDAO implements IGroupe {

    private final Connection connection;
    private final MatiereService matiereService;
    private final EnseignantService enseignantService;

    public GroupeDAO(Connection connection, MatiereService matiereService, EnseignantService enseignantService) {
        this.connection = connection;
        this.matiereService = matiereService;
        this.enseignantService = enseignantService;
    }

    @Override
    public List<Groupe> getAllGroupes() throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT * FROM groupe";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idMatiere = resultSet.getInt("id_matiere");
                int idEnseignant = resultSet.getInt("id_enseignant");
                int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");
                String nomGroupe = resultSet.getString("nom_groupe");

                Matiere matiere = matiereService.getMatiereById(idMatiere);
                Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);

                Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                groupe.setNom_groupe(nomGroupe);
                groupes.add(groupe);
            }
        }
        return groupes;
    }

    @Override
    public Groupe getGroupeByNom(String nomGroupe) throws SQLException {
        String query = "SELECT * FROM groupe WHERE nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nomGroupe);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idMatiere = resultSet.getInt("id_matiere");
                    int idEnseignant = resultSet.getInt("id_enseignant");
                    int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");

                    Matiere matiere = matiereService.getMatiereById(idMatiere);
                    Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);

                    Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                    groupe.setNom_groupe(nomGroupe);
                    return groupe;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Groupe> getGroupesByMatiereAndEnseignant(int idMatiere, int idEnseignant) throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT * FROM groupe WHERE id_matiere = ? AND id_enseignant = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMatiere);
            preparedStatement.setInt(2, idEnseignant);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");
                    String nomGroupe = resultSet.getString("nom_groupe");

                    Matiere matiere = matiereService.getMatiereById(idMatiere);
                    Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);

                    Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                    groupe.setNom_groupe(nomGroupe);
                    groupes.add(groupe);
                }
            }
        }
        return groupes;
    }

    @Override
    public List<Groupe> getGroupesByCriteria(Map<String, Object> criteria) throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM groupe WHERE 1=1");

        if (criteria.containsKey("id_matiere")) {
            query.append(" AND id_matiere = ?");
        }
        if (criteria.containsKey("id_enseignant")) {
            query.append(" AND id_enseignant = ?");
        }
        if (criteria.containsKey("nb_max_etudiant")) {
            query.append(" AND nb_max_etudiant = ?");
        }
        if (criteria.containsKey("nom_groupe")) {
            query.append(" AND nom_groupe = ?");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (criteria.containsKey("id_matiere")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id_matiere"));
            }
            if (criteria.containsKey("id_enseignant")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("id_enseignant"));
            }
            if (criteria.containsKey("nb_max_etudiant")) {
                preparedStatement.setInt(index++, (Integer) criteria.get("nb_max_etudiant"));
            }
            if (criteria.containsKey("nom_groupe")) {
                preparedStatement.setString(index++, (String) criteria.get("nom_groupe"));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idMatiere = resultSet.getInt("id_matiere");
                    int idEnseignant = resultSet.getInt("id_enseignant");
                    int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");
                    String nomGroupe = resultSet.getString("nom_groupe");

                    Matiere matiere = matiereService.getMatiereById(idMatiere);
                    Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);

                    Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                    groupe.setNom_groupe(nomGroupe);
                    groupes.add(groupe);
                }
            }
        }
        return groupes;
    }

    @Override
    public List<Groupe> getGroupesByMatiere(int idMatiere) throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT * FROM groupe WHERE id_matiere = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMatiere);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idEnseignant = resultSet.getInt("id_enseignant");
                    int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");
                    String nomGroupe = resultSet.getString("nom_groupe");

                    Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);
                    Matiere matiere = matiereService.getMatiereById(idMatiere);

                    Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                    groupe.setNom_groupe(nomGroupe);
                    groupes.add(groupe);
                }
            }
        }
        return groupes;
    }

    @Override
    public List<Groupe> getGroupesByEnseignant(int idEnseignant) throws SQLException {
        List<Groupe> groupes = new ArrayList<>();
        String query = "SELECT * FROM groupe WHERE id_enseignant = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idEnseignant);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idMatiere = resultSet.getInt("id_matiere");
                    int nbMaxEtudiant = resultSet.getInt("nb_max_etudiant");
                    String nomGroupe = resultSet.getString("nom_groupe");

                    Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);
                    Matiere matiere = matiereService.getMatiereById(idMatiere);

                    Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                    groupe.setNom_groupe(nomGroupe);
                    groupes.add(groupe);
                }
            }
        }
        return groupes;
    }

    @Override
    public void saveGroupe(Groupe groupe) throws SQLException {
        String query = "INSERT INTO groupe (id_matiere, id_enseignant, nb_max_etudiant, nom_groupe) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupe.getMatiere().getId());
            preparedStatement.setInt(2, groupe.getEnseignant().getId());
            preparedStatement.setInt(3, groupe.getNbMaxEtudiant());
            preparedStatement.setString(4, groupe.getNom_groupe());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateGroupe(Groupe groupe) throws SQLException {
        String query = "UPDATE groupe SET id_matiere = ?, id_enseignant = ?, nb_max_etudiant = ? WHERE nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupe.getMatiere().getId());
            preparedStatement.setInt(2, groupe.getEnseignant().getId());
            preparedStatement.setInt(3, groupe.getNbMaxEtudiant());
            preparedStatement.setString(4, groupe.getNom_groupe());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteGroupe(String nomGroupe) throws SQLException {
        String query = "DELETE FROM groupe WHERE nom_groupe = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nomGroupe);

            preparedStatement.executeUpdate();
        }
    }
}
