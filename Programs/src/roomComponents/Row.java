package src.roomComponents;

import src.persons.Person;
import src.persons.PersonsGroup;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int sceneDistance;
    private RowGroup rowGroup;
    private List<Seat> seats; // pas forcément utile
    private int availableSeats;
    private boolean used;

    private int numRow;

    public Row(RowGroup rowGroup, List<Seat> s, int d, int numRow) {
        this.rowGroup = rowGroup;
        this.sceneDistance = d;
        this.seats = s;
        this.availableSeats = s.size();
        this.used = false;
        this.numRow = numRow;
    }

    /**
     * Creates a blank copy of a Row, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the Row won't be associated anymore.
     * Also, all variables giving information about the Row's fullness will be reset.
     */
    public Row copy() {
        ArrayList<Seat> seatsList = new ArrayList<>();
        for (int i = 0; i < this.seats.size(); i++) {
            seatsList.add(new Seat());
        }
        return new Row(this.rowGroup, seatsList, this.getSceneDistance(), this.getNumRow());
    }

    public boolean enoughFor(int nbPersons) {
        return availableSeats >= nbPersons;
    }

    /**
     * Ajoute un nombre de personnes sur la ligne
     *
     * @param group le group de personnes à ajouter à la ligne
     * @param peopleDistance     distance minimum entre deux groupes de personnes
     * @return true si l'ajout est réussi, faux sinon (pas assez de place sur la ligne).
     */
    public boolean addPersonsGroup(PersonsGroup group, int peopleDistance) {
        if (enoughFor(group.getNbPersons())) {
            // le premier siège auquel on peut ajouter de nouvelles personnes,
            // en considérant que le remplissage se fait de la gauche vers la droite pour des index allant de 0 à n.
            int currentSeat = this.getCapacity() - this.availableSeats;

            // ajout de chaque personne du groupe à partir du premier siège disponible
            for (Person person : group.getPersons()) {
                person.setSeat(this.getSeats().get(currentSeat));
                this.getSeats().get(currentSeat).setPerson(person);
                this.availableSeats--;
                currentSeat++;
            }

            // on marque les sièges indisponibles pour chaque siège compris dans la distance minimale
            for (int i = 0; i < peopleDistance; i++) {
                if (currentSeat < this.getCapacity()) {
                    this.getSeats().get(currentSeat).setOutOfOrder(true);
                    this.availableSeats--;
                    currentSeat++;
                }
            }
            // la rangée est marquée comme utilisée
            this.used = true;
            // l'ajout a pu se faire car il y avait assez de place sur la rangée
            return true;
        } else {
            // l'ajout ne peut pas se faire car il n'y a pas assez de place sur la rangée
            return false;
        }
    }

    public boolean isUsed() {
        return used;
    }

    public RowGroup getRowGroup() {
        return rowGroup;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getSceneDistance() {
        return sceneDistance;
    }

    public int getCapacity() {
        return seats.size();
    }

    public int getNumRow() {
        return numRow;
    }

    public void setRowGroup(RowGroup rowGroup) {
        this.rowGroup = rowGroup;
    }

    @Override
    public String toString() {
        return "G" + this.getRowGroup().getNumGroup() + "-R" + this.getNumRow();
    }
}
