package src.utils;

import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Problem;
import src.roomComponents.Room;
import src.roomComponents.Row;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public static AbstractProblem createProblem(String folderName, int reservationSpecified) {
        //accéder au fichier

        //créations des élèments du problème
        Room room = createRoom(filePath);
        int[] contraints = createContraints(filePath);
        List<PersonsGroup> reservations = createReservations(filePath);

        //créer un objet de classe Problem avec les valeurs récupérée au dessus
        Problem problem = new Problem(folderName, reservations, contraints[0], contraints[1], contraints[2], room);

        return problem;
    }

    private static int[] createContraints(String filePath) {

    }

    private static List<PersonsGroup> createReservations(String filePath) {
        //lecture de contraintes01.txt
        try {
            URL path = Foo.class.getResource(".../Data/" + FilePath+"contraintes01.txt");
            File file = new File(path.getFile());
            Scanner myReader = new Scanner(file);

            //lire les données de fichier
            String rowString;
            String rowGroupString;
            List<String> placesDistanceString = new ArrayList<>();


            rowString = myReader.nextLine(); //lire première ligne "G rangées
            rowGroupString = myReader.nextLine(); //lire seconde ligne "Rg rangées"
            while (myReader.hasNextLine()) {
                //algo de bouclage sur les rangées
                placesDistanceString.add(myReader.nextLine()); // on lit chaque ligne et on stocke le nb de place et la distance
            }

            myReader.close();
        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
        }
    }


    private static Room createRoom(String filePath) {
        //lecture de salleXX.txt
        try {
            URL path = Foo.class.getResource(".../Data/" + FilePath + FilePath + ".txt");
            File file = new File(path.getFile());
            Scanner myReader = new Scanner(file);

            //lire les données de fichier
            String rowString;
            String rowGroupString;
            List<String> placesDistanceString = new ArrayList<>();


            rowString = myReader.nextLine(); //lire première ligne "G rangées
            rowGroupString = myReader.nextLine(); //lire seconde ligne "Rg rangées"
            while (myReader.hasNextLine()) {
                //algo de bouclage sur les rangées
                placesDistanceString.add(myReader.nextLine()); // on lit chaque ligne et on stocke le nb de place et la distance
            }

            myReader.close();
        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
        }

        return Null;
    }
}