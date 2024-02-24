package src.utils;

import src.algorithms.AbstractAlgo;
import src.algorithms.AlgoHeuristique1;
import src.main.Runner;
import src.persons.Person;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Problem;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;
import src.roomComponents.Seat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Checker {

    public static void checkValidity(String resultPath) {
        resultPath = "Results/" + resultPath;
        String[] pathSplited = resultPath.split("-");
        int roomSpecified = Integer.parseInt((pathSplited[1]).substring(1));
        int reservationSpecified = Integer.parseInt((pathSplited[2]).substring(1));
        String algoName = (pathSplited[3]);
        Problem testProblem = Parser.createProblem("Salle" + (pathSplited[1]).substring(1), reservationSpecified);// Room
                                                                                                                  // pour
                                                                                                                  // tester
                                                                                                                  // le
        // problème

        String firstLine = "";
        String lastLine = "";
        List<String> fillingData = new ArrayList<>();

        try {
            File file = new File(resultPath);
            Scanner myReader = new Scanner(file);
            firstLine = myReader.nextLine();
            while (myReader.hasNextLine()) {
                fillingData.add(myReader.nextLine());
            }
            myReader.close();

        } catch (FileNotFoundException e) { // en cas d'erreur sur la lecture
            System.out.println("Could not read data from file.");
            e.printStackTrace();
            System.exit(1);
        }

        lastLine = fillingData.get(fillingData.size() - 1);// nb de personnes non placées
        fillingData.remove(fillingData.size() - 1);
        fillingData.remove(fillingData.size() - 1);

        // On traite les données récupérées et on fait le remplissage.
        String[] firstLineSplitted = firstLine.split(" ");

        int nbRowUsed = Integer.parseInt(firstLineSplitted[0]);

        String[] fillingRateSplitted = firstLineSplitted[2].split("/");// on peut pas caster "X/Y" directement
        int numerator = Integer.parseInt(fillingRateSplitted[0]);
        // int denominator = Integer.parseInt(fillingRateSplitted[1]);
        // double fillingRate = numerator / denominator;

        // int nbNotSeated = Integer.parseInt(lastLine);// todo vérifier que c'est good

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

                for (int k = 0; k < specGroup.getNbPersons(); j++) {
                    Person currentP = specGroup.getPersons().get(k);
                    currentRow.getSeats().get(j).setPerson(currentP);
                    currentP.setSeat(currentRow.getSeats().get(j));
                    k++;
                }
                for (int contQ = 0; j < currentRow.getSeats().size()
                        && contQ < testProblem.getPeopleDistance(); contQ++) {
                    currentRow.getSeats().get(j).setOutOfOrder(true);
                    j++;
                }
            }
            // currentRow = testProblem.getRoom().getRowGroup(numGroup).getRow(numRow);
            // System.out.println(testProblem.getRoom().toString());
            // Test de la contrainte P
            if (oldNumGroup == numGroup) {
                if (!(numRow - oldNumRow > testProblem.getRowDistance())) {
                    throw new RuntimeException(
                            "\n !!!Nombre de personnes dans un même rang ne respecte pas les contraintes de remplissage.!!!");
                }

            }
            for (int p = 0; p < testProblem.getRowDistance()
                    && p + numRow < testProblem.getRoom().getRowGroup(numGroup - 1).getNbRows(); p++) {
                currentRow = testProblem.getRoom().getRowGroup(numGroup - 1).getRow(numRow + p);
                for (Seat s : currentRow.getSeats()) {
                    s.setOutOfOrder(true);
                }
            }

            // test de la contrainte P

            oldNumGroup = numGroup;
            oldNumRow = numRow;

            // test de la contrainte K
            if (!(specGroup.getNbPersons() <= testProblem.getMaxGroupSize())) {
                throw new RuntimeException(
                        "\n !!!Nombre de personnes dans un même rang ne respecte pas les contraintes de remplissage.!!!");
            }

            // test de la contrainte Q
            if (!(nbPerson + (testProblem.getPeopleDistance() * (nbPersonGroup - 1)) <= currentRow.getCapacity())) {
                throw new RuntimeException(
                        "\n !!!Nombre de personnes dans un même rang ne respecte pas les contraintes de remplissage.!!!");
            }
        }

        if (!(numerator == nbSeatFilled)) { // Test du taux de remplissage
            throw new RuntimeException("\n !!!Taux de remplissage ne correspond pas aux données de remplissage.!!!");
        }

        System.out.println(
                "### Row Groups de solution : " + testProblem.getName() + " | "
                        + algoName
                        + " ###\n");
        for (RowGroup rowGroup : testProblem.getRoom().getRowGroups()) {
            System.out.println("\nRow Group:");
            for (Row row : rowGroup.getRows()) {
                System.out.println(row.getSeats() + " " + row.getSceneDistance());
            }
        }

        System.out
                .println("\n Toutes les contraintes ont été respectées !!");
    }

    public static void main(String[] args) {

        Checker.checkValidity(
                "Remplissage-S04-R03-AlgoHeuristique1-D2024-02-24T20h06.res");

    }

}
