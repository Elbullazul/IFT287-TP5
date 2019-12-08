// Travail fait par :
// NomEquipier1 - Matricule
// NomEquipier2 - Matricule

package JardinCollectif;

import java.io.*;
import java.util.StringTokenizer;

import Gestionnaires.GestionnaireJardinCollectif;

import java.sql.*;

/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 7 juillet 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class JardinCollectif
{

    public static GestionnaireJardinCollectif gestionJardin;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 4)
        {
            System.out.println(
                    "Usage: java JardinCollectif.JardinCollectif <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        gestionJardin = new GestionnaireJardinCollectif(args[0], args[1], args[2], args[3]);

        // Il est possible que vous ayez à déplacer la connexion ailleurs.
        // N'hésitez pas à le faire!

        BufferedReader reader = ouvrirFichier(args);
        String transaction = lireTransaction(reader);
        while (!finTransaction(transaction))
        {
            executerTransaction(transaction);
            transaction = lireTransaction(reader);
        }
        
        gestionJardin.fermer();
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try
        {
            System.out.println(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".
                if (command.equals("aide"))
                {
                    afficherAide();
                }
                // membres
                else if (command.equals("inscrireMembre"))
                {
                    // Lire les parametres ici et appeler la bonne methode
                    // de traitement pour la transaction
                    String param1 = readString(tokenizer);
                    String param2 = readString(tokenizer);
                    String param3 = readString(tokenizer);
                    Integer param4 = readInt(tokenizer);
                    Integer param5 = readInt(tokenizer);

                    try
                    {
                        System.out.println(gestionJardin.getGestionMembre());
                        gestionJardin.getGestionMembre().InscrireMembre(param1, param2, param3, param4, param5);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if (command.equals("supprimerMembre"))
                {
                    Integer param1 = readInt(tokenizer);

                    gestionJardin.getGestionMembre().supprimerMembre(param1);
                }
                else if (command.equals("afficherMembres"))
                {
                    gestionJardin.getGestionMembre().AfficheMembres();
                }
                else if (command.equals("promouvoirAdministrateur"))
                {
                    Integer param1 = readInt(tokenizer);

                    gestionJardin.getGestionMembre().Promouvoir(param1);
                }
                // lots
                else if (command.equals("accepterDemande"))
                {
                    String param1 = readString(tokenizer);
                    Integer param2 = readInt(tokenizer);

                    gestionJardin.getGestionDemande().accepterDemande(param1, param2);
                }
                else if (command.equals("refuserDemande"))
                {
                    String param1 = readString(tokenizer);
                    Integer param2 = readInt(tokenizer);

                    gestionJardin.getGestionDemande().refuserDemande(param1, param2);
                }
                else if (command.equals("ajouterLot"))
                {
                    String param1 = readString(tokenizer);
                    Integer param2 = readInt(tokenizer);

                    gestionJardin.gestionLot.AjoutLot(param1, param2);
                }
                else if (command.equals("rejoindreLot"))
                {
                    String param1 = readString(tokenizer);
                    Integer param2 = readInt(tokenizer);

                    gestionJardin.gestionAttribution.rejoindreLot(param1, param2);
                }
                else if (command.equals("supprimerLot"))
                {
                    String param1 = readString(tokenizer);

                    gestionJardin.gestionLot.SupprimerLot(param1);
                }
                else if (command.equals("afficherLots"))
                {
                    gestionJardin.gestionLot.AfficheLots();
                }
                else if (command.equals("ajouterPlante"))
                {
                    String param1 = readString(tokenizer);
                    Integer param2 = readInt(tokenizer);

                    gestionJardin.gestionPlante.AjouterPlante(param1, param2);
                }

                else if (command.equals("planterPlante"))
                {
                    String param1 = readString(tokenizer);
                    String param2 = readString(tokenizer);
                    Integer param3 = readInt(tokenizer);
                    Integer param4 = readInt(tokenizer);
                    Date param5 = readDate(tokenizer);

                    gestionJardin.gestionCulture.planterplante(param1, param2, param3, param4, param5);
                }
                else if (command.equals("recolterPlante"))
                {
                    String param1 = readString(tokenizer);
                    String param2 = readString(tokenizer);
                    Integer param3 = readInt(tokenizer);

                    gestionJardin.gestionCulture.recolterplante(param1, param2, param3);
                }
                else if (command.equals("afficherPlantesLot"))
                {
                    String param1 = readString(tokenizer);

                    gestionJardin.gestionCulture.affichePlanteLot(param1);
                }
                else if (command.equals("retirerPlante"))
                {
                    String param1 = readString(tokenizer);

                    gestionJardin.gestionPlante.RetirerPlante(param1);
                }
                else if (command.equals("afficherPlantes"))
                {
                    gestionJardin.gestionPlante.Afficheplantes();
                }
                else
                {
                    System.out.println(" : Transaction non reconnue");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des
            // problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas
            // apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
        }
    }

    /**
     * Affiche le menu des transactions acceptees par le systeme
     */
    private static void afficherAide()
    {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("Les transactions sont:");
        System.out.println("  aide");
        System.out.println("  quitter");
        System.out.println("  inscrireMembre <prenom> <nom> <motDePasse> <noMembre>");
        System.out.println("  supprimerMembre <noMembre>");
        System.out.println("  promouvoirAdministrateur <noMembre>");
        System.out.println("  ajouterLot <nomLot> <nbMaxMembre>");
        System.out.println("  supprimerLot <nomLot>");
        System.out.println("  rejoindreLot <nomLot> <noMembre>");
        System.out.println("  accepterDemande <nomLot> <noMembre>");
        System.out.println("  refuserDemande <nomLot> <noMembre>");
        System.out.println("  ajouterPlante <nomPlante> <tempsDeCulture>");
        System.out.println("  retirerPlante <nomPlante>");
        System.out.println("  planterPlante <nomPlante> <nomLot> <noMembre> <nbExemplaires><datePlantation>");
        System.out.println("  recolterPlante <nomPlante> <nomLot> <noMembre>");
        System.out.println("  afficherMembres");
        System.out.println("  afficherPlantes");
        System.out.println("  afficherLots");
        System.out.println("  afficherPlantesLot <lot>");

    }

    // ****************************************************************
    // * Les methodes suivantes n'ont pas besoin d'etre modifiees *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Integer.valueOf(token).intValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Date.valueOf(token);
            }
            catch (IllegalArgumentException e)
            {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

}
