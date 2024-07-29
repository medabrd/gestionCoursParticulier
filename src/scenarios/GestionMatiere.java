package scenarios;
import dataBase.DataBaseConnection;
import dataBase.MatiereDAO;
import models.Matiere;
import services.MatiereService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GestionMatiere {

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
            System.out.println("Menu de gestion des matières :");
            System.out.println("1. Ajouter une matière");
            System.out.println("2. Modifier une matière");
            System.out.println("3. Supprimer une matière");
            System.out.println("4. Afficher toutes les matières");
            System.out.println("5. Afficher une matière par ID");
            System.out.println("6. Afficher les matières par critères");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choix) {
                case 1:
                    ajouterMatiere(connection);
                    break;
                case 2:
                    modifierMatiere(connection);
                    break;
                case 3:
                    supprimerMatiere(connection);
                    break;
                case 4:
                    afficherToutesLesMatieres(connection);
                    break;
                case 5:
                    afficherMatiereParId(connection);
                    break;
                case 6:
                    afficherMatieresParCritere(connection);
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 7);
    }

    public static void ajouterMatiere(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir le libellé de la matière : ");
        String libelle = scanner.nextLine();

        System.out.print("Veuillez saisir le niveau de la matière : ");
        String niveau = scanner.nextLine();

        System.out.print("Veuillez saisir la section de la matière : ");
        String section = scanner.nextLine();

        // Création de l'objet Matiere
        Matiere matiere = new Matiere(libelle, niveau, section);

        // Instance DAO et service
        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        // Appel à la méthode de sauvegarde de la matière (dans votre service)
        try {
            matiereService.saveMatiere(matiere);
            System.out.println("Matière ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la matière : " + e.getMessage());
        }
    }

    public static void afficherToutesLesMatieres(Connection connection) {
        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        try {
            List<Matiere> toutesLesMatieres = matiereService.getAllMatieres();
            if (toutesLesMatieres.isEmpty()) {
                System.out.println("Aucune matière n'est enregistrée.");
            } else {
                System.out.println("Liste de toutes les matières :");
                for (Matiere matiere : toutesLesMatieres) {
                    System.out.println(matiere);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des matières : " + e.getMessage());
        }
    }

    public static void afficherMatiereParId(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de la matière : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        try {
            Matiere matiere = matiereService.getMatiereById(id);
            if (matiere != null) {
                System.out.println("Informations de la matière :");
                System.out.println(matiere);
            } else {
                System.out.println("Aucune matière trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la matière : " + e.getMessage());
        }
    }

    public static void afficherMatieresParCritere(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> criteria = new java.util.HashMap<>();

        System.out.print("Voulez-vous filtrer par ID ? (oui/non) : ");
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            System.out.print("Veuillez saisir l'ID : ");
            criteria.put("id", scanner.nextInt());
            scanner.nextLine();
        }

        System.out.print("Voulez-vous filtrer par libellé ? (oui/non) : ");
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            System.out.print("Veuillez saisir le libellé : ");
            criteria.put("libelle", scanner.nextLine());
        }

        System.out.print("Voulez-vous filtrer par niveau ? (oui/non) : ");
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            System.out.print("Veuillez saisir le niveau : ");
            criteria.put("niveau", scanner.nextLine());
        }

        System.out.print("Voulez-vous filtrer par section ? (oui/non) : ");
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            System.out.print("Veuillez saisir la section : ");
            criteria.put("section", scanner.nextLine());
        }

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        try {
            List<Matiere> matieres = matiereService.getMatieresByCriteria(criteria);
            if (matieres.isEmpty()) {
                System.out.println("Aucune matière trouvée avec les critères spécifiés.");
            } else {
                System.out.println("Liste des matières correspondant aux critères :");
                for (Matiere matiere : matieres) {
                    System.out.println(matiere);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des matières par critères : " + e.getMessage());
        }
    }

    public static void modifierMatiere(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir l'ID de la matière à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        try {
            Matiere matiereAModifier = matiereService.getMatiereById(id);
            if (matiereAModifier != null) {
                System.out.println("Informations de la matière à modifier :");
                System.out.println(matiereAModifier);

                // Demander les nouvelles informations à l'utilisateur
                System.out.print("Nouveau libellé : ");
                String nouveauLibelle = scanner.nextLine();
                matiereAModifier.setLibelle(nouveauLibelle);

                System.out.print("Nouveau niveau : ");
                String nouveauNiveau = scanner.nextLine();
                matiereAModifier.setNiveau(nouveauNiveau);

                System.out.print("Nouvelle section : ");
                String nouvelleSection = scanner.nextLine();
                matiereAModifier.setSection(nouvelleSection);

                matiereService.updateMatiere(matiereAModifier);
                System.out.println("Matière modifiée avec succès !");
            } else {
                System.out.println("Aucune matière trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la matière : " + e.getMessage());
        }
    }

    public static void supprimerMatiere(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Souhaitez-vous supprimer par ID (1) ou par libellé (2) ? ");
        int choixSuppression = scanner.nextInt();
        scanner.nextLine();

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        try {
            if (choixSuppression == 1) {
                System.out.print("Veuillez saisir l'ID de la matière à supprimer : ");
                int id = scanner.nextInt();
                scanner.nextLine();
                matiereService.deleteMatiere(id);
                System.out.println("Matière supprimée avec succès !");
            } else if (choixSuppression == 2) {
                System.out.print("Veuillez saisir le libellé de la matière à supprimer : ");
                String libelle = scanner.nextLine();

                // Rechercher la matière par libellé
                Map<String, Object> criteria = new java.util.HashMap<>();
                criteria.put("libelle", libelle);
                List<Matiere> matieres = matiereService.getMatieresByCriteria(criteria);

                if (matieres.isEmpty()) {
                    System.out.println("Aucune matière trouvée avec le libellé spécifié.");
                } else {
                    for (Matiere matiere : matieres) {
                        matiereService.deleteMatiere(matiere.getId());
                        System.out.println("Matière supprimée avec succès : " + matiere);
                    }
                }
            } else {
                System.out.println("Choix invalide.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la matière : " + e.getMessage());
        }
    }
}
