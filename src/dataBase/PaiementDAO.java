    package dataBase;
    import contracts.IPaiement;
    import models.Paiement;
    import models.Etudiant;
    import java.sql.*;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import services.EtudiantService;

    public class PaiementDAO implements IPaiement {

        private final Connection connection;
        private final EtudiantService etudiantService;

        public PaiementDAO(Connection connection, EtudiantService etudiantService) {
            this.connection = connection;
            this.etudiantService = etudiantService;
        }

        @Override
        public List<Paiement> getAllPaiements() throws SQLException {
            List<Paiement> paiements = new ArrayList<>();
            String query = "SELECT * FROM paiement";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDate datePaiement = resultSet.getDate("date_paiement").toLocalDate();
                    double montant = resultSet.getDouble("montant");
                    int idEtudiant = resultSet.getInt("id_etudiant");

                    Etudiant etudiant = etudiantService.getEtudiantById(idEtudiant);

                    Paiement paiement = new Paiement(datePaiement, montant, etudiant);
                    paiement.setId(id);
                    paiements.add(paiement);
                }
            }
            return paiements;
        }

        @Override
        public Paiement getPaiementById(int id) throws SQLException {
            String query = "SELECT * FROM paiement WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LocalDate datePaiement = resultSet.getDate("date_paiement").toLocalDate();
                        double montant = resultSet.getDouble("montant");
                        int idEtudiant = resultSet.getInt("id_etudiant");

                        Etudiant etudiant = etudiantService.getEtudiantById(idEtudiant);

                        Paiement paiement = new Paiement(datePaiement, montant, etudiant);
                        paiement.setId(id);
                        return paiement;
                    } else {
                        return null;
                    }
                }
            }
        }

        @Override
        public List<Paiement> getPaiementsByEtudiantId(int etudiantId) throws SQLException {
            List<Paiement> paiements = new ArrayList<>();
            String query = "SELECT * FROM paiement WHERE id_etudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, etudiantId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        LocalDate datePaiement = resultSet.getDate("date_paiement").toLocalDate();
                        double montant = resultSet.getDouble("montant");

                        Etudiant etudiant = etudiantService.getEtudiantById(etudiantId);

                        Paiement paiement = new Paiement(datePaiement, montant, etudiant);
                        paiement.setId(id);
                        paiements.add(paiement);
                    }
                }
            }
            return paiements;
        }

        @Override
        public List<Paiement> getPaiementsByDate(LocalDate date) throws SQLException {
            List<Paiement> paiements = new ArrayList<>();
            String query = "SELECT * FROM paiement WHERE date_paiement = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(date));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        double montant = resultSet.getDouble("montant");
                        int idEtudiant = resultSet.getInt("id_etudiant");

                        Etudiant etudiant = etudiantService.getEtudiantById(idEtudiant);

                        Paiement paiement = new Paiement(date, montant, etudiant);
                        paiement.setId(id);
                        paiements.add(paiement);
                    }
                }
            }
            return paiements;
        }

        @Override
        public void savePaiement(Paiement paiement) throws SQLException {
            String query = "INSERT INTO paiement (id_etudiant, date_paiement, montant) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, paiement.getEtudiant().getId());
                preparedStatement.setDate(2, Date.valueOf(paiement.getDate_paiement()));
                preparedStatement.setDouble(3, paiement.getMontant());

                preparedStatement.executeUpdate();
            }
        }

        @Override
        public void updatePaiement(Paiement paiement) throws SQLException {
            String query = "UPDATE paiement SET id_etudiant = ?, date_paiement = ?, montant = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, paiement.getEtudiant().getId());
                preparedStatement.setDate(2, Date.valueOf(paiement.getDate_paiement()));
                preparedStatement.setDouble(3, paiement.getMontant());
                preparedStatement.setInt(4, paiement.getId());

                preparedStatement.executeUpdate();
            }
        }

        @Override
        public void deletePaiement(int id) throws SQLException {
            String query = "DELETE FROM paiement WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            }
        }
    }
