package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Class represents listener of the {@link GeometricalObject} class.
 */
public interface GeometricalObjectListener {

    /**
     * Method is called when object has changed.
     *
     * @param o object that changed
     */
    void geometricalObjectChanged(GeometricalObject o);
}