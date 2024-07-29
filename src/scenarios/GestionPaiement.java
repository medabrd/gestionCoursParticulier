package scenarios;

import dataBase.DataBaseConnection;
import dataBase.EtudiantDAO;
import dataBase.PaiementDAO;
import models.Etudiant;
import models.Paiement;
import services.EtudiantService;
import services.PaiementService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GestionPaiement {

    public static void main(String[] args) {

        Connection connection ;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu de gestion des paiements :");
            System.out.println("1. Ajouter un paiement");
            System.out.println("2. Modifier un paiement");
            System.out.println("3. Supprimer un paiement");
            System.out.println("4. Afficher tous les paiements");
            System.out.println("5. Afficher paiements par étudiant");
            System.out.println("6. Afficher paiements par date");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    ajouterPaiement(connection);
                    break;
                case 2:
                    modifierPaiement(connection);
                    break;
                case 3:
                    supprimerPaiement(connection);
                    break;
                case 4:
                    afficherTousLesPaiements(connection);
                    break;
                case 5:
                    afficherPaiementsParEtudiant(connection);
                    break;
                case 6:
                    afficherPaiementsParDate(connection);
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 7);
    }

    public static void ajouterPaiement(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        // Afficher la liste des étudiants disponibles
        EtudiantDAO etudiantDAO=new EtudiantDAO(connection ) ;
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);
        try {
            List<Etudiant> tousLesEtudiants = etudiantService.getAllEtudiants();
            if (tousLesEtudiants.isEmpty()) {
                System.out.println("Aucun étudiant n'est enregistré.");
            } else {
                System.out.println("Liste de tous les étudiants :");
                for (Etudiant etudiant : tousLesEtudiants) {
                    System.out.println(etudiant); // Assurez-vous que la méthode toString() de Etudiant est bien implémentée
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
        }

        System.out.print("Veuillez saisir l'ID de l'étudiant : ");
        int idEtudiant = scanner.nextInt();

        System.out.print("Veuillez saisir la date de paiement (YYYY-MM-DD) : ");
        String dateStr = scanner.next();
        LocalDate datePaiement = LocalDate.parse(dateStr);

        System.out.print("Veuillez saisir le montant : ");
        double montant = scanner.nextDouble();

        // Récupérer l'étudiant depuis l'ID
        try {
            Etudiant etudiant = etudiantService.getEtudiantById(idEtudiant);

            // Créer l'objet Paiement
            Paiement paiement = new Paiement(datePaiement, montant, etudiant);

            // Instancier le service
            PaiementService paiementService = new PaiementService(new PaiementDAO(connection, etudiantService));

            // Enregistrer le paiement
            paiementService.savePaiement(paiement);
            System.out.println("Paiement ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du paiement : " + e.getMessage());
        }
    }

    public static void afficherTousLesPaiements(Connection connection) {
        PaiementService paiementService = new PaiementService(new PaiementDAO(connection, new EtudiantService(new EtudiantDAO(connection))));

        try {
            List<Paiement> tousLesPaiements = paiementService.getAllPaiements();
            if (tousLesPaiements.isEmpty()) {
                System.out.println("Aucun paiement n'est enregistré.");
            } else {
                System.out.println("Liste de tous les paiements :");
                for (Paiement paiement : tousLesPaiements) {
                    System.out.println(paiement);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
    }

    public static void afficherPaiementsParEtudiant(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        // Afficher la liste des étudiants disponibles
        EtudiantDAO etudiantDAO=new EtudiantDAO(connection ) ;
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);
        try {
            List<Etudiant> tousLesEtudiants = etudiantService.getAllEtudiants();
            if (tousLesEtudiants.isEmpty()) {
                System.out.println("Aucun étudiant n'est enregistré.");
            } else {
                System.out.println("Liste de tous les étudiants :");
                for (Etudiant etudiant : tousLesEtudiants) {
                    System.out.println(etudiant); // Assurez-vous que la méthode toString() de Etudiant est bien implémentée
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
        }
        System.out.print("Veuillez saisir l'ID de l'étudiant : ");
        int idEtudiant = scanner.nextInt();

        PaiementService paiementService = new PaiementService(new PaiementDAO(connection, new EtudiantService(new EtudiantDAO(connection))));

        try {
            List<Paiement> paiements = paiementService.getPaiementsByEtudiantId(idEtudiant);
            if (paiements.isEmpty()) {
                System.out.println("Aucun paiement trouvé pour l'étudiant avec l'ID " + idEtudiant);
            } else {
                System.out.println("Paiements de l'étudiant ID " + idEtudiant + " :");
                for (Paiement paiement : paiements) {
                    System.out.println(paiement);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
    }

    public static void afficherPaiementsParDate(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir la date de paiement (YYYY-MM-DD) : ");
        String dateStr = scanner.next();
        LocalDate datePaiement = LocalDate.parse(dateStr);

        PaiementService paiementService = new PaiementService(new PaiementDAO(connection, new EtudiantService(new EtudiantDAO(connection))));

        try {
            List<Paiement> paiements = paiementService.getPaiementsByDate(datePaiement);
            if (paiements.isEmpty()) {
                System.out.println("Aucun paiement trouvé pour la date " + datePaiement);
            } else {
                System.out.println("Paiements de la date " + datePaiement + " :");
                for (Paiement paiement : paiements) {
                    System.out.println(paiement);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
    }

    public static void modifierPaiement(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        afficherTousLesPaiements(connection);
        System.out.print("Veuillez saisir l'ID du paiement à modifier : ");
        int idPaiement = scanner.nextInt();

        PaiementService paiementService = new PaiementService(new PaiementDAO(connection, new EtudiantService(new EtudiantDAO(connection))));

        try {
            Paiement paiementAModifier = paiementService.getPaiementById(idPaiement);
            if (paiementAModifier != null) {
                System.out.println("Informations du paiement à modifier :");
                System.out.println(paiementAModifier);

                System.out.print("Nouvelle date de paiement (YYYY-MM-DD) : ");
                String dateStr = scanner.next();
                LocalDate nouvelleDate = LocalDate.parse(dateStr);
                paiementAModifier.setDate_paiement(nouvelleDate);

                System.out.print("Nouveau montant : ");
                double nouveauMontant = scanner.nextDouble();
                paiementAModifier.setMontant(nouveauMontant);

                paiementService.updatePaiement(paiementAModifier);
                System.out.println("Paiement modifié avec succès !");
            } else {
                System.out.println("Aucun paiement trouvé avec l'ID " + idPaiement);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du paiement : " + e.getMessage());
        }
    }

    public static void supprimerPaiement(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        afficherTousLesPaiements(connection);
        System.out.print("Veuillez saisir l'ID du paiement à supprimer : ");
        int idPaiement = scanner.nextInt();

        PaiementService paiementService = new PaiementService(new PaiementDAO(connection, new EtudiantService(new EtudiantDAO(connection))));

        try {
            paiementService.deletePaiement(idPaiement);
            System.out.println("Paiement supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du paiement : " + e.getMessage());
        }
    }
}
