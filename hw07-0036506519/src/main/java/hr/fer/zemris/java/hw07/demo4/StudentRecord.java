package hr.fer.zemris.java.hw07.demo4;

/**
 * Represents record of the student.
 *
 * @author Stjepan Kovačić
 */
public class StudentRecord {

    /**
     * Jmbag of the student.
     */
    private String jmbag;
    /**
     * Last name of the student.
     */
    private String prezime;
    /**
     * First name of the student.
     */
    private String ime;
    /**
     * Midterm points of the student.
     */
    private double bodoviMI;
    /**
     * Final points of the student.
     */
    private double bodoviZI;
    /**
     * Lab points of the student.
     */
    private double bodoviLAB;
    /**
     * Grade of the student.
     */
    private int ocjena;

    public StudentRecord(String jmbag, String prezime, String ime,
                         double bodoviMI, double bodoviZI, double bodoviLAB, int ocjena) {
        this.jmbag = jmbag;
        this.prezime = prezime;
        this.ime = ime;
        this.bodoviMI = bodoviMI;
        this.bodoviZI = bodoviZI;
        this.bodoviLAB = bodoviLAB;
        this.ocjena = ocjena;
    }

    public String getJmbag() {
        return jmbag;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getIme() {
        return ime;
    }

    public double getBodoviMI() {
        return bodoviMI;
    }

    public double getBodoviZI() {
        return bodoviZI;
    }

    public double getBodoviLAB() {
        return bodoviLAB;
    }

    public int getOcjena() {
        return ocjena;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(jmbag).append(", ").append(prezime).append(", ").append(ime).append(", ").append(getBodoviMI())
                .append(", ").append(getBodoviZI()).append(", ").append(getBodoviLAB()).append(", ")
                .append(getOcjena());

        return sb.toString();
    }
}
