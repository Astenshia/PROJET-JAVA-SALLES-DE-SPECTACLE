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
     * @param q     distance minimum entre deux groupes de personnes
     * @return true si l'ajout est réussi, faux sinon (pas assez de place sur la ligne).
     */
    public boolean addPersonsGroup(PersonsGroup group, int q) {
        if (enoughFor(group.getNbPersons())) {
            // ajout de chaque personne du groupe
            for (Person person : group.getPersons()) {
                person.setSeat(this.getSeats().get(this.getCapacity() - this.availableSeats));
                this.getSeats().get(this.getCapacity() - this.availableSeats).setPerson(person);
                availableSeats--;
            }


            for (int i = 0; i < q; i++) {
                if (this.getCapacity() - this.availableSeats < this.getCapacity()) {
                    this.getSeats().get(this.getCapacity() - this.availableSeats).setOutOfOrder(true);
                    availableSeats--;
                }
            }
            used = true;
            return true;

        } else {
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
