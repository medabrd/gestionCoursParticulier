package scenarios;

import dataBase.DataBaseConnection;
import dataBase.EnseignantDAO;
import models.Enseignant;
import services.EnseignantService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestionEnseignant {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu de gestion des enseignants :");
            System.out.println("1. Ajouter un enseignant");
            System.out.println("2. Modifier un enseignant");
            System.out.println("3. Supprimer un enseignant");
            System.out.println("4. Afficher tous les enseignants");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choix) {
                case 1:
                    ajouterEnseignant(connection);
                    break;
                case 2:
                    modifierEnseignant(connection);
                    break;
                case 3:
                    System.out.println("Souhaitez-vous supprimer par ID (1) ou par email (2) ?");
                    int choixSuppression = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (choixSuppression == 1) {
                        supprimerEnseignantParId(connection);
                    } else if (choixSuppression == 2) {
                        supprimerEnseignantParMail(connection);
                    } else {
                        System.out.println("Choix invalide.");
                    }
                    break;
                case 4:
                    System.out.println("Souhaitez-vous afficher tous les enseignants (1), par ID (2) ou par email (3) ?");
                    int choixAffichage = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (choixAffichage == 1) {
                        afficherTousLesEnseignants(connection);
                    } else if (choixAffichage == 2) {
                        afficherEnseignantParId(connection);
                    } else if (choixAffichage == 3) {
                        afficherEnseignantParMail(connection);
                    } else {
                        System.out.println("Choix invalide.");
                    }
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 5);
    }

    public static void ajouterEnseignant(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'enseignant : ");
        String email = scanner.nextLine();

        System.out.print("Veuillez saisir le nom de l'enseignant : ");
        String nom = scanner.nextLine();

        System.out.print("Veuillez saisir le prénom de l'enseignant : ");
        String prenom = scanner.nextLine();

        // Création de l'objet Enseignant
        Enseignant enseignant = new Enseignant(email, nom, prenom);

        // Instance DAO et service
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        // Appel à la méthode de sauvegarde de l'enseignant (dans votre service)
        try {
            enseignantService.saveEnseignant(enseignant);
            System.out.println("Enseignant ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'enseignant : " + e.getMessage());
        }
    }

    public static void afficherTousLesEnseignants(Connection connection) {
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            List<Enseignant> tousLesEnseignants = enseignantService.getAllEnseignants();
            if (tousLesEnseignants.isEmpty()) {
                System.out.println("Aucun enseignant n'est enregistré.");
            } else {
                System.out.println("Liste de tous les enseignants :");
                for (Enseignant enseignant : tousLesEnseignants) {
                    System.out.println(enseignant); // Assurez-vous que la méthode toString() de Enseignant est bien implémentée
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
        }
    }

    public static void afficherEnseignantParId(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de l'enseignant : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            Enseignant enseignant = enseignantService.getEnseignantById(id);
            if (enseignant != null) {
                System.out.println("Informations de l'enseignant :");
                System.out.println(enseignant);
            } else {
                System.out.println("Aucun enseignant trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'enseignant : " + e.getMessage());
        }
    }

    public static void afficherEnseignantParMail(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'enseignant : ");
        String email = scanner.nextLine();

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            Enseignant enseignant = enseignantService.getEnseignantByMail(email);
            if (enseignant != null) {
                System.out.println("Informations de l'enseignant :");
                System.out.println(enseignant);
            } else {
                System.out.println("Aucun enseignant trouvé avec l'email " + email);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'enseignant : " + e.getMessage());
        }
    }

    public static void modifierEnseignant(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'enseignant à modifier : ");
        String email = scanner.nextLine();

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            Enseignant enseignantAModifier = enseignantService.getEnseignantByMail(email);
            if (enseignantAModifier != null) {
                System.out.println("Informations de l'enseignant à modifier :");
                System.out.println(enseignantAModifier);

                // Demander les nouvelles informations à l'utilisateur
                System.out.print("Nouveau nom : ");
                String nouveauNom = scanner.nextLine();
                enseignantAModifier.setNom(nouveauNom);

                System.out.print("Nouveau prénom : ");
                String nouveauPrenom = scanner.nextLine();
                enseignantAModifier.setPrenom(nouveauPrenom);

                System.out.print("Nouvel email : ");
                String nouvelEmail = scanner.nextLine();
                enseignantAModifier.setEmail(nouvelEmail);

                enseignantService.updateEnseignant(enseignantAModifier);
                System.out.println("Enseignant modifié avec succès !");
            } else {
                System.out.println("Aucun enseignant trouvé avec l'email " + email);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'enseignant : " + e.getMessage());
        }
    }

    public static void supprimerEnseignantParId(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de l'enseignant à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            enseignantService.deleteEnseignant(id);
            System.out.println("Enseignant supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'enseignant : " + e.getMessage());
        }
    }

    public static void supprimerEnseignantParMail(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'enseignant à supprimer : ");
        String email = scanner.nextLine();

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        try {
            enseignantService.deleteEnseignant(email);
            System.out.println("Enseignant supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'enseignant : " + e.getMessage());
        }
    }
}
