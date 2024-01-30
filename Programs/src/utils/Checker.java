package src.utils;

import src.problems.Problem;
import src.roomComponents.Room;
import src.roomComponents.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Checker {

    public void checkValidity(String roomFolderName, int reservationSpecified, String resultData) {
        Problem testProblem = Parser.createProblem(roomFolderName, reservationSpecified);

        String firstLine = "";
        String lastLine = "";
        List<String> fillingData = new ArrayList<>();

        try {
            String path = "Results/" + roomFolderName + "/" + roomFolderName + ".txt";//TODO ON VERRA UNE FOIS QU'ON S'EST MIS D'ACCORD
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            firstLine = myReader.nextLine();
            while (myReader.hasNextLine()) {
                fillingData.add(myReader.nextLine());
            }
            lastLine = fillingData.get(fillingData.size() - 1);//nb de personnes non placées
            fillingData.remove(fillingData.size() - 1);
            myReader.close();

        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);
        }

        //On traite les données récupérées et on fait le remplissage.
        String[] firstLineSplitted = firstLine.split(" ");

        int nbRowUsed = Integer.parseInt(firstLineSplitted[0]);

        String[] fillingRateSplitted = firstLineSplitted[2].split("/");//on peut pas caster "X/Y" directement
        int numerator = Integer.parseInt(fillingRateSplitted[0]);
        int denominator = Integer.parseInt(fillingRateSplitted[1]);
        //double fillingRate =  numerator / denominator;

        int nbNotSeated = Integer.parseInt(lastLine);//todo vérifier que c'est good

        try {
            assert (nbRowUsed == fillingData.size());
        } catch (RuntimeException e) {
            System.out.println("Nombre de rangées utilisées ne correspond pas aux données de remplissage.");
            e.printStackTrace();
            System.exit(1);
        }


        int nbSeatFilled = 0;
        int numGroup, numRow, specGroup;
        Row currentRow;
        for (String data : fillingData) {
            String[] dataSplit = data.split(" ");
            numGroup = Integer.parseInt(dataSplit[0]);
            numRow = Integer.parseInt(dataSplit[1]);
            nbSeatFilled += Integer.parseInt(dataSplit[dataSplit.length - 1]);

            //currentRow = testProblem.getRoom().getRowGroup(numGroup).getRow(numRow); //todo pouvoir accéder a la row depuis le num de row et de groupe
            //int sizePersonGroup = testProblem.getReservations().get(specGroup);
            //todo continuer et essayer de comprendre (impossible)

            //remplissage de current row

        }

        try {
            assert (numerator == nbSeatFilled);
            //assert(denominator == testProblem.getRoom().getNbOfSeats());
        } catch (RuntimeException e) {
            System.out.println("Taux de remplissage ne correspond pas aux données de remplissage.");
            e.printStackTrace();
            System.exit(1);
        }


    }
}
