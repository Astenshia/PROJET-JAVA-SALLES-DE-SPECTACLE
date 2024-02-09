package src.utils;

import src.algorithms.AbstractAlgo;
import src.algorithms.AlgoHeuristique1;
import src.main.Runner;
import src.persons.Person;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Problem;
import src.roomComponents.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Checker {

    public static void checkValidity(int roomSpecified, int reservationSpecified, AbstractAlgo algoSpecified) {
        String roomString = String.valueOf(roomSpecified);
        String reservationString = String.valueOf(reservationSpecified);
        if (roomSpecified < 10) {
            roomString = "0" + roomString;
        }
        if (reservationSpecified < 10) {
            reservationString = "0" + reservationString;
        }
        Problem testProblem = Parser.createProblem("Salle" + roomString, reservationSpecified);//Room pour tester le problème

        String firstLine = "";
        String lastLine = "";
        List<String> fillingData = new ArrayList<>();

        try {
            String path = "Results/Remplissage-S" + roomString + "-R" + reservationString + "-" + algoSpecified.getClass().getSimpleName() + "-D-09-02-2024.16:49.res";//TODO enlever date
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            firstLine = myReader.nextLine();
            while (myReader.hasNextLine()) {
                fillingData.add(myReader.nextLine());
            }
            myReader.close();


        } catch (FileNotFoundException e) { //en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);
        }

        lastLine = fillingData.get(fillingData.size() - 1);//nb de personnes non placées
        fillingData.remove(fillingData.size() - 1);
        fillingData.remove(fillingData.size() - 1);

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
        int numGroup, numRow, nbPerson;
        int oldNumRow = 0;
        int oldNumGroup = 0;
        int nbPersonGroup = 0;
        List<PersonsGroup> reservation = testProblem.getReservations();
        PersonsGroup specGroup = null;
        Row currentRow = null;
        for (String data : fillingData) {
            String[] dataSplit = data.split(" ");
            numGroup = Integer.parseInt(dataSplit[0]);
            numRow = Integer.parseInt(dataSplit[1]);
            nbSeatFilled += Integer.parseInt(dataSplit[dataSplit.length - 1]);
            nbPerson = 0;
            nbPersonGroup = 0;
            int j = 0;
            currentRow = testProblem.getRoom().getRowGroup(numGroup - 1).getRow(numRow - 1);
            for (int i = 2; i < dataSplit.length - 1; i++) {
                specGroup = reservation.get(Integer.parseInt(dataSplit[i]) - 1);
                nbPerson += specGroup.getNbPersons();
                nbPersonGroup++;

                for (int k = 0; j < specGroup.getNbPersons(); j++) {
                    Person currentP = specGroup.getPersons().get(k);
                    currentRow.getSeats().get(j).setPerson(currentP);
                    currentP.setSeat(currentRow.getSeats().get(j));
                    k++;
                }
                j = j + testProblem.getPeopleDistance();
            }
            //currentRow = testProblem.getRoom().getRowGroup(numGroup).getRow(numRow);
            //System.out.println(testProblem.getRoom().toString());
            //Test de la contrainte P
            if (oldNumGroup == numGroup) {
                try {
                    assert (numRow - oldNumRow > testProblem.getRowDistance());
                    System.out.println("Distance entre deux rang d'un même groupe respecte les contraintes de remplissage.");
                } catch (RuntimeException e) {
                    System.out.println("!!!Distance entre deux rang d'un même groupe ne respecte pas les contraintes de remplissage.!!!");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            oldNumGroup = numGroup;
            oldNumRow = numRow;

            //test de la contrainte Q
            try {
                assert (specGroup.getNbPersons() <= testProblem.getMaxGroupSize());
                System.out.println("Distance entre deux rang d'un même groupe respecte les contraintes de remplissage.");
            } catch (RuntimeException e) {
                System.out.println("!!!Distance entre deux rang d'un même groupe ne respecte pas les contraintes de remplissage.!!!");
                e.printStackTrace();
                System.exit(1);
            }

            //test de la contrainte K
            try {
                assert (nbPerson + (testProblem.getPeopleDistance() * (nbPersonGroup - 1)) <= currentRow.getCapacity());
                System.out.println("Nombre de personnes dans un même rang respecte les contraintes de remplissage.");

            } catch (RuntimeException e) {
                System.out.println("!!!Nombre de personnes dans un même rang ne respecte pas les contraintes de remplissage.!!!");
                e.printStackTrace();
                System.exit(1);
            }


        }

        try {
            assert (numerator == nbSeatFilled);
            System.out.println("Taux de remplissage correct");
            //assert(denominator == testProblem.getRoom().getNbOfSeats());
        } catch (RuntimeException e) {
            System.out.println("Taux de remplissage ne correspond pas aux données de remplissage.");
            e.printStackTrace();
            System.exit(1);
        }


    }

    public static void main(String[] args) {

        Checker.checkValidity(4, 3, new AlgoHeuristique1());

    }
}
