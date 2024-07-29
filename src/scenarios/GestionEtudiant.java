package scenarios;
import dataBase.DataBaseConnection;
import dataBase.EtudiantDAO;
import models.Etudiant;
import services.EtudiantService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestionEtudiant {


    public static void main(String[] args) {

        Connection connection = null ;
        try{
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu de gestion des étudiants :");
            System.out.println("1. Ajouter un étudiant");
            System.out.println("2. Modifier un étudiant");
            System.out.println("3. Supprimer un étudiant");
            System.out.println("4. Afficher tous les étudiants");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            // ... (le reste du code est identique)

            switch (choix) {
                case 1:
                    ajouterEtudiant(connection);
                    break;
                case 2:
                    modifierEtudiant(connection);
                    break;
                case 3:
                    System.out.println("Souhaitez-vous supprimer par ID (1) ou par email (2) ?");
                    int choixSuppression = scanner.nextInt();
                    if (choixSuppression == 1) {
                        supprimerEtudiantParId(connection);
                    } else if (choixSuppression == 2) {
                        supprimerEtudiantParMail(connection);
                    } else {
                        System.out.println("Choix invalide.");
                    }
                    break;
                case 4:
                    System.out.println("Souhaitez-vous afficher tous les étudiants (1), par ID (2) ou par email (3) ?");
                    int choixAffichage = scanner.nextInt();
                    if (choixAffichage == 1) {
                        afficherTousLesEtudiants(connection);
                    } else if (choixAffichage == 2) {
                        afficherEtudiantParId(connection);
                    } else if (choixAffichage == 3) {
                        afficherEtudiantParMail(connection);
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

    // Méthodes pour les opérations sur les étudiants
    public static void ajouterEtudiant(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'étudiant : ");
        String email = scanner.nextLine();

        System.out.print("Veuillez saisir le nom de l'étudiant : ");
        String nom = scanner.nextLine();

        System.out.print("Veuillez saisir le prénom de l'étudiant : ");
        String prenom = scanner.nextLine();

        // Création de l'objet Etudiant
        Etudiant etudiant = new Etudiant(email, nom, prenom);


        // instance DAO et service
        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        // Appel à la méthode de sauvegarde de l'étudiant (dans votre service)
        try {
            etudiantService.saveEtudiant(etudiant);
            System.out.println("Étudiant ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
        }
    }

    public static void afficherTousLesEtudiants(Connection connection) {
        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
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
    }

    public static void afficherEtudiantParId(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de l'étudiant : ");
        int id = scanner.nextInt();

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        try {
            Etudiant etudiant = etudiantService.getEtudiantById(id);
            if (etudiant != null) {
                System.out.println("Informations de l'étudiant :");
                System.out.println(etudiant);
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'étudiant : " + e.getMessage());
        }
    }

    public static void afficherEtudiantParMail(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'étudiant : ");
        String email = scanner.nextLine();

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        try {
            Etudiant etudiant = etudiantService.getEtudiantByMail(email);
            if (etudiant != null) {
                System.out.println("Informations de l'étudiant :");
                System.out.println(etudiant);
            } else {
                System.out.println("Aucun étudiant trouvé avec l'email " + email);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'étudiant : " + e.getMessage());
        }
    }

    public static void modifierEtudiant(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'étudiant à modifier : ");
        String email = scanner.nextLine();

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        try {
            Etudiant etudiantAModifier = etudiantService.getEtudiantByMail(email);
            if (etudiantAModifier != null) {
                System.out.println("Informations de l'étudiant à modifier :");
                System.out.println(etudiantAModifier);

                // Demander les nouvelles informations à l'utilisateur
                System.out.print("Nouveau nom : ");
                String nouveauNom = scanner.nextLine();
                etudiantAModifier.setNom(nouveauNom);

                System.out.print("Nouveau prénom : ");
                String nouveauPrenom = scanner.nextLine();
                etudiantAModifier.setPrenom(nouveauPrenom);

                System.out.print("Nouveau email : ");
                String nouvelEmail = scanner.nextLine();
                etudiantAModifier.setEmail(nouvelEmail);


                etudiantService.updateEtudiant(etudiantAModifier);
                System.out.println("Étudiant modifié avec succès !");
            } else {
                System.out.println("Aucun étudiant trouvé avec l'email " + email);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'étudiant : " + e.getMessage());
        }
    }

    public static void supprimerEtudiantParId(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de l'étudiant à supprimer : ");
        int id = scanner.nextInt();

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        try {
            etudiantService.deleteEtudiant(id);
            System.out.println("Étudiant supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
        }
    }

    public static void supprimerEtudiantParMail(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'email de l'étudiant à supprimer : ");
        String email = scanner.nextLine();

        EtudiantDAO etudiantDAO = new EtudiantDAO(connection);
        EtudiantService etudiantService = new EtudiantService(etudiantDAO);

        try {
            etudiantService.deleteEtudiant(email);
            System.out.println("Étudiant supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
        }
    }

}


