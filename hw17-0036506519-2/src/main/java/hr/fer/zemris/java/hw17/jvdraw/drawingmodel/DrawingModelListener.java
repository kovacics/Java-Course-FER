package hr.fer.zemris.java.hw17.jvdraw.drawingmodel;

/**
 * Interface represents listener of the drawing model.
 */
public interface DrawingModelListener {

    /**
     * Method is called when objects are added to the model.
     *
     * @param source model
     * @param index0 start index
     * @param index1 end index
     */
    void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Method is called when objects are removed from the model.
     *
     * @param source model
     * @param index0 start index
     * @param index1 end index
     */
    void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Method is called when objects are changed in the model.
     *
     * @param source model
     * @param index0 start index
     * @param index1 end index
     */
    void objectsChanged(DrawingModel source, int index0, int index1);
}