package utils;

import problems.AbstractProblem;
import roomComponents.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public static AbstractProblem createProblem(String filePath){
        //accéder au fichier

        try {
            URL path = Foo.class.getResource(".../Data/"+FilePath);
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

        //créer un objet de classe Problem avec
        AbstractProblem

        return Null;
    }

    public static void readReservation(String filePath) {

    }
}
