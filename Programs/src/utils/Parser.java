package src.utils;

import src.persons.Person;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Problem;
import src.roomComponents.Room;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;
import src.roomComponents.Seat;

import java.io.*;
import java.util.*;

public class Parser {

    /**
     * @param folderName           uniquement le nom de la salle (comme "Salle01"), sans les slashs
     * @param reservationSpecified le numero des contraintes choisies
     * @return un objet problème
     */
    public static Problem createProblem(String folderName, int reservationSpecified) {
        //accéder au fichier

        //créations des elements du problème
        Room room = createRoom(folderName);
        int[] constraints = createConstraints(folderName);
        List<PersonsGroup> reservations = createReservations(folderName, reservationSpecified);

        //nommage du probleme au format : Problem-S<nbSalle>-R<nbReservation>
        String folderNumber = folderName.substring(folderName.length() - 2);
        String sb = "Problem-S" + folderNumber + "-R" + reservationSpecified;

        //créer et retourner un objet de classe Problem avec les valeurs récupérées au-dessus
        return new Problem(sb, reservations, constraints[0], constraints[1], constraints[2], room);
    }

    private static int[] createConstraints(String filePath) {
        int p, k, q;
        String line = "";

        //On lit le fichier pour obtenir les 3 valeurs p k et q sous forme de String
        try {
            File file = new File("Data/" + filePath + "/Contraintes01..txt");
            Scanner myReader = new Scanner(file);

            line = myReader.nextLine(); //lire première ligne avec k p q récupéré en String
            myReader.close();
        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);

        }

        //split de la ligne pour séparer les nombres, et on les convertit en int
        String[] pkq = line.split(" ");
        p = Integer.parseInt(pkq[0]);
        k = Integer.parseInt(pkq[1]);
        q = Integer.parseInt(pkq[2]);

        //on crée un tableau pour les stocker
        int[] constraints = new int[3];
        constraints[0] = p;
        constraints[1] = k;
        constraints[2] = q;

        //on renvoie le tableau des contraintes
        return constraints;
    }

    private static List<PersonsGroup> createReservations(String filePath, int reservationSpecified) {
        String nb = String.valueOf(reservationSpecified);
        if (reservationSpecified < 10) {
            nb = "0" + nb;
        }

        String nbResString = "";
        String SpecString = "";
        //lecture de ReservationXX.txt
        try {
            File file = new File("Data/" + filePath + "/Reservations" + nb + ".txt");

            Scanner myReader = new Scanner(file);

            //lire les données de fichier
            nbResString = myReader.nextLine(); //lire première ligne "G rangées
            SpecString = myReader.nextLine(); //lire seconde ligne "Rg rangées"

            myReader.close();
        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);
        }

        //on crée la liste qu'on va return
        List<PersonsGroup> reservations = new ArrayList<>();

        //on divise toutes les reservations en string[] contenant le nombre de personnes par reservation dans une case du tableau
        String[] specsTabString = SpecString.split(" ");
        int nbRes = Integer.parseInt(nbResString);

        //on itère sur le tableau pour générer les personnes et les mettre dans des PersonsGroup
        for (int i = 0; i < nbRes; i++) {
            int nbPers = Integer.parseInt(specsTabString[i]);

            // on crée un nouveau groupe de Persons
            // les Persons seront créés par le constructeur de PersonsGroup
            reservations.add(new PersonsGroup(nbPers, i + 1));
        }
        return reservations;
    }


    public static Room createRoom(String filePath) {
        //Initialisation de variable avant lecture
        String groupString = "";
        String rowGroupString = "";
        List<String> placesDistanceString = new ArrayList<>();
        // ListIterator<String> pds = placesDistanceString.listIterator(); // c'est mieux que get() mais je n'arrive pas à le faire fonctionner

        //lecture de salleXX.txt
        try {
            String path = "Data/" + filePath + "/" + filePath + ".txt";
            File file = new File(path);

            Scanner myReader = new Scanner(file);

            //lire les données de fichier
            groupString = myReader.nextLine(); //lire première ligne "G rangées" (ou G groupes)
            rowGroupString = myReader.nextLine(); //lire seconde ligne "Rg rangées"
            while (myReader.hasNextLine()) {                //algo de bouclage sur les rangées
                placesDistanceString.add(myReader.nextLine()); // on lit chaque ligne et on stocke le nb de place et la distance
            }

            myReader.close();
        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);
        }

        //Traitement des données lues

        //On récupère le nombre de rangées
        int nbOfGroups = Integer.parseInt(groupString); //raw groups

        //On crée la liste du nombre de rangées par groupe
        String[] rowGroupParsed = rowGroupString.split(" ");
        int[] Groups = new int[nbOfGroups];
        for (int i = 0; i < nbOfGroups; i++) {
            Groups[i] = Integer.parseInt(rowGroupParsed[i]);
        }

        //On récupère la liste des sièges
        List<Row> rows;
        List<RowGroup> groupsList = new ArrayList<>();
        List<Seat> s;

        //On doit maintenant créer pour chaque groupe sa liste de rangée, et pour chaque rangée sa liste de siège et les distance

        //Double boucle, on boucle sur le nombre de groupes puis sur le nombre de rangées dans un groupe
        for (int grp_ite = 0; grp_ite < nbOfGroups; grp_ite++) {//boucle sur le nombre de groupes

            rows = new ArrayList<>();
            int j = 0;//compteur de ligne pour pas relire la meme ligne
            for (int row_ite = 0; row_ite < Groups[grp_ite]; row_ite++) {//Boucle sur le nombre de rangées par groupe
                s = new ArrayList<>();
                String[] currentRow = placesDistanceString.get(j).split(" ");
                int nbSeats = Integer.parseInt(currentRow[0]);
                int distance = Integer.parseInt(currentRow[1]);

                for (int i = 0; i < nbSeats; i++) {//On instancie nbSeats sièges
                    s.add(new Seat());
                }

                rows.add(new Row(s, distance, row_ite + 1));
                j++;
            }

            groupsList.add(new RowGroup(rows, grp_ite + 1));
        }


        return new Room(groupsList);
    }
}
