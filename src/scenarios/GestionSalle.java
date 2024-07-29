package scenarios;

import dataBase.DataBaseConnection;
import dataBase.SalleDAO;
import models.Salle;
import services.SalleService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestionSalle {

    public static void main(String[] args) {
        Connection connection ;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu de gestion des salles :");
            System.out.println("1. Ajouter une salle");
            System.out.println("2. Modifier une salle");
            System.out.println("3. Supprimer une salle");
            System.out.println("4. Afficher toutes les salles");
            System.out.println("5. Afficher une salle par numéro");
            System.out.println("6. Afficher les salles par capacité");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterSalle(connection);
                    break;
                case 2:
                    modifierSalle(connection);
                    break;
                case 3:
                    supprimerSalle(connection);
                    break;
                case 4:
                    afficherToutesLesSalles(connection);
                    break;
                case 5:
                    afficherSalleParNumero(connection);
                    break;
                case 6:
                    afficherSallesParCapacite(connection);
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 7);
    }

    public static void ajouterSalle(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir le numéro de la salle : ");
        int numSalle = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Veuillez saisir la capacité de la salle : ");
        int capacite = scanner.nextInt();
        scanner.nextLine();

        // Création de l'objet Salle
        Salle salle = new Salle(numSalle, capacite);

        // Instance DAO et service
        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        // Appel à la méthode de sauvegarde de la salle (dans votre service)
        try {
            salleService.saveSalle(salle);
            System.out.println("Salle ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la salle : " + e.getMessage());
        }
    }

    public static void afficherToutesLesSalles(Connection connection) {
        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        try {
            List<Salle> toutesLesSalles = salleService.getAllSalles();
            if (toutesLesSalles.isEmpty()) {
                System.out.println("Aucune salle n'est enregistrée.");
            } else {
                System.out.println("Liste de toutes les salles :");
                for (Salle salle : toutesLesSalles) {
                    System.out.println(salle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des salles : " + e.getMessage());
        }
    }

    public static void afficherSalleParNumero(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir le numéro de la salle : ");
        int numSalle = scanner.nextInt();
        scanner.nextLine();

        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        try {
            Salle salle = salleService.getSalleById(numSalle);
            if (salle != null) {
                System.out.println("Informations de la salle :");
                System.out.println(salle);
            } else {
                System.out.println("Aucune salle trouvée avec le numéro " + numSalle);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la salle : " + e.getMessage());
        }
    }

    public static void afficherSallesParCapacite(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir la capacité maximale : ");
        int capaciteMax = scanner.nextInt();
        scanner.nextLine();

        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        try {
            List<Salle> salles = salleService.getSallesByCapacite(capaciteMax);
            if (salles.isEmpty()) {
                System.out.println("Aucune salle trouvée avec une capacité maximale de " + capaciteMax);
            } else {
                System.out.println("Liste des salles correspondant aux critères :");
                for (Salle salle : salles) {
                    System.out.println(salle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des salles par capacité : " + e.getMessage());
        }
    }

    public static void modifierSalle(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir le numéro de la salle à modifier : ");
        int numSalle = scanner.nextInt();
        scanner.nextLine();

        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        try {
            Salle salleAModifier = salleService.getSalleById(numSalle);
            if (salleAModifier != null) {
                System.out.println("Informations de la salle à modifier :");
                System.out.println(salleAModifier);

                // Demander les nouvelles informations à l'utilisateur
                System.out.print("Nouvelle capacité : ");
                int nouvelleCapacite = scanner.nextInt();
                scanner.nextLine();
                salleAModifier.setCapacite(nouvelleCapacite);

                salleService.updateSalle(salleAModifier);
                System.out.println("Salle modifiée avec succès !");
            } else {
                System.out.println("Aucune salle trouvée avec le numéro " + numSalle);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la salle : " + e.getMessage());
        }
    }

    public static void supprimerSalle(Connection connection) {
        Scanner scanner = new Scanner(System.in);


        SalleDAO salleDAO = new SalleDAO(connection);
        SalleService salleService = new SalleService(salleDAO);

        try {
                System.out.print("Veuillez saisir le numéro de la salle à supprimer : ");
                int numSalle = scanner.nextInt();
                scanner.nextLine();
                salleService.deleteSalle(numSalle);
                System.out.println("Salle supprimée avec succès !");
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }
}
