package scenarios;
import dataBase.DataBaseConnection;
import dataBase.GroupeDAO;
import dataBase.MatiereDAO;
import dataBase.EnseignantDAO;
import models.Groupe;
import models.Matiere;
import models.Enseignant;
import services.GroupeService;
import services.MatiereService;
import services.EnseignantService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestionGroupe {

    public static void main(String[] args) {
        Connection connection;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu de gestion des groupes :");
            System.out.println("1. Ajouter un groupe");
            System.out.println("2. Modifier un groupe");
            System.out.println("3. Supprimer un groupe");
            System.out.println("4. Afficher tous les groupes");
            System.out.println("5. Afficher les groupes par critère");
            System.out.println("6. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choix) {
                case 1:
                    ajouterGroupe(connection);
                    break;
                case 2:
                    modifierGroupe(connection);
                    break;
                case 3:
                    supprimerGroupe(connection);
                    break;
                case 4:
                    afficherTousLesGroupes(connection);
                    break;
                case 5:
                    afficherGroupeParCritere(connection);
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 6);
    }

    public static void ajouterGroupe(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        // Afficher toutes les matières disponibles
        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        System.out.println("Liste des matières disponibles :");
        try {
            List<Matiere> matieres = matiereService.getAllMatieres();
            for (Matiere matiere : matieres) {
                System.out.println("ID: " + matiere.getId() + ", Libellé: " + matiere.getLibelle() + ", Niveau: " + matiere.getNiveau() + ", Section: " + matiere.getSection());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des matières : " + e.getMessage());
            return;
        }

        System.out.print("Veuillez saisir l'ID de la matière : ");
        int idMatiere = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Afficher tous les enseignants disponibles
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        System.out.println("Liste des enseignants disponibles :");
        try {
            List<Enseignant> enseignants = enseignantService.getAllEnseignants();
            for (Enseignant enseignant : enseignants) {
                System.out.println("ID: " + enseignant.getId() + ", Nom: " + enseignant.getNom() + ", Prénom: " + enseignant.getPrenom());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
            return;
        }

        System.out.print("Veuillez saisir l'ID de l'enseignant : ");
        int idEnseignant = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Veuillez saisir le nombre maximum d'étudiants : ");
        int nbMaxEtudiant = scanner.nextInt();
        scanner.nextLine();


        GroupeDAO groupeDAO = new GroupeDAO(connection,matiereService, enseignantService);
        GroupeService groupeService = new GroupeService(groupeDAO);



        try {
            Matiere matiere = matiereService.getMatiereById(idMatiere);
            Enseignant enseignant = enseignantService.getEnseignantById(idEnseignant);

            if (matiere != null && enseignant != null) {
                Groupe groupe = new Groupe(nbMaxEtudiant, enseignant, matiere);
                groupeService.saveGroupe(groupe);
                System.out.println("Groupe ajouté avec succès !");
            } else {
                System.out.println("La matière ou l'enseignant spécifié n'existe pas.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du groupe : " + e.getMessage());
        }
    }

    public static void afficherTousLesGroupes(Connection connection) {

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);

        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);

        GroupeDAO groupeDAO = new GroupeDAO(connection,matiereService, enseignantService);
        GroupeService groupeService = new GroupeService(groupeDAO);

        try {
            List<Groupe> tousLesGroupes = groupeService.getAllGroupes();
            if (tousLesGroupes.isEmpty()) {
                System.out.println("Aucun groupe n'est enregistré.");
            } else {
                System.out.println("Liste de tous les groupes :");
                for (Groupe groupe : tousLesGroupes) {
                    System.out.println(groupe);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des groupes : " + e.getMessage());
        }
    }

    public static void afficherGroupeParCritere(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);
        GroupeDAO groupeDAO = new GroupeDAO(connection, matiereService, enseignantService);
        GroupeService groupeService = new GroupeService(groupeDAO);

        try {
            // Demander à l'utilisateur de choisir un critère
            System.out.println("Choisissez le critère de recherche :");
            System.out.println("1. Par matière");
            System.out.println("2. Par enseignant");
            System.out.print("Votre choix : ");
            int critere = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (critere == 1) {
                // Afficher les matières avec leurs IDs
                System.out.println("Liste des matières disponibles :");
                List<Matiere> matieres;
                try {
                    matieres = matiereService.getAllMatieres();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la récupération des matières : " + e.getMessage());
                    return; // Terminer la méthode en cas d'erreur
                }
                for (Matiere matiere : matieres) {
                    System.out.println(matiere);
                }

                System.out.print("Veuillez saisir l'ID de la matière : ");
                int idMatiere = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character


                List<Groupe> groupes = groupeService.getGroupesByMatiere(idMatiere);
                if (groupes.isEmpty()) {
                    System.out.println("Aucun groupe trouvé pour cette matière.");
                } else {
                    System.out.println("Groupes pour la matière " + matiereService.getMatiereById(idMatiere) + " :");
                    for (Groupe groupe : groupes) {
                        System.out.println(groupe);
                    }
                }

            } else if (critere == 2) {
                // Afficher les enseignants avec leurs IDs
                System.out.println("Liste des enseignants disponibles :");
                List<Enseignant> enseignants;
                try {
                    enseignants = enseignantService.getAllEnseignants();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
                    return; // Terminer la méthode en cas d'erreur
                }
                for (Enseignant enseignant : enseignants) {
                    System.out.println("ID: " + enseignant.getId() + ", Nom: " + enseignant.getNom() + " " + enseignant.getPrenom());
                }

                System.out.print("Veuillez saisir l'ID de l'enseignant : ");
                int idEnseignant = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character


                List<Groupe> groupes = groupeService.getGroupesByEnseignant(idEnseignant);
                if (groupes.isEmpty()) {
                    System.out.println("Aucun groupe trouvé pour cet enseignant.");
                } else {
                    System.out.println("Groupes pour l'enseignant " +enseignantService.getEnseignantById(idEnseignant).getId()+" "
                            +enseignantService.getEnseignantById(idEnseignant).getNom()+" "+
                            enseignantService.getEnseignantById(idEnseignant).getPrenom() + " :");
                    for (Groupe groupe : groupes) {
                        System.out.println(groupe);
                    }
                }

            } else {
                System.out.println("Critère invalide.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des groupes par critère : " + e.getMessage());
        }
    }

    public static void modifierGroupe(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);
        GroupeDAO groupeDAO = new GroupeDAO(connection, matiereService, enseignantService);
        GroupeService groupeService = new GroupeService(groupeDAO);

        try {
            // Affiche la liste de tous les groupes
            List<Groupe> tousLesGroupes = groupeService.getAllGroupes();
            if (tousLesGroupes.isEmpty()) {
                System.out.println("Aucun groupe n'est enregistré.");
                return;
            }

            System.out.println("Liste de tous les groupes :");
            for (Groupe groupe : tousLesGroupes) {
                System.out.println(groupe.getNom_groupe());
            }

            // Demande à l'utilisateur de saisir le nom du groupe à modifier
            System.out.print("Veuillez saisir le nom du groupe à modifier : ");
            String nomGroupe = scanner.nextLine();

            // Recherche le groupe par son nom
            Groupe groupeAModifier = groupeService.getGroupeByNom(nomGroupe);

            if (groupeAModifier != null) {
                System.out.println("Informations du groupe à modifier :");
                System.out.println(groupeAModifier);

                System.out.print("Nouveau nombre maximum d'étudiants : ");
                int nouveauNbMaxEtudiant = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                groupeAModifier.setNbMaxEtudiant(nouveauNbMaxEtudiant);

                groupeService.updateGroupe(groupeAModifier);
                System.out.println("Groupe modifié avec succès !");
            } else {
                System.out.println("Le groupe spécifié n'existe pas.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du groupe : " + e.getMessage());
        }
    }

    public static void supprimerGroupe(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        MatiereDAO matiereDAO = new MatiereDAO(connection);
        MatiereService matiereService = new MatiereService(matiereDAO);
        EnseignantDAO enseignantDAO = new EnseignantDAO(connection);
        EnseignantService enseignantService = new EnseignantService(enseignantDAO);
        GroupeDAO groupeDAO = new GroupeDAO(connection, matiereService, enseignantService);
        GroupeService groupeService = new GroupeService(groupeDAO);

        try {
            // Affiche la liste de tous les groupes
            List<Groupe> tousLesGroupes = groupeService.getAllGroupes();
            if (tousLesGroupes.isEmpty()) {
                System.out.println("Aucun groupe n'est enregistré.");
                return;
            }

            System.out.println("Liste de tous les groupes :");
            for (Groupe groupe : tousLesGroupes) {
                System.out.println(groupe.getNom_groupe());
            }

            // Demande à l'utilisateur de saisir le nom du groupe à supprimer
            System.out.print("Veuillez saisir le nom du groupe à supprimer : ");
            String nomGroupe = scanner.nextLine();

            // Recherche le groupe par son nom
            Groupe groupeASupprimer = groupeService.getGroupeByNom(nomGroupe);

            if (groupeASupprimer != null) {
                groupeService.deleteGroupe(groupeASupprimer.getNom_groupe());
                System.out.println("Groupe supprimé avec succès !");
            } else {
                System.out.println("Le groupe spécifié n'existe pas.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du groupe : " + e.getMessage());
        }
    }

}
