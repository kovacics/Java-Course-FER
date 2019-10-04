package hr.fer.zemris.java.hw17.jvdraw.drawingmodel;


import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

import java.nio.file.Path;

/**
 * Interface represents drawing model used for storing elements.
 */
@SuppressWarnings("unused")
public interface DrawingModel {

    /**
     * Returns number of the elements in the model.
     *
     * @return elements number
     */
    int getSize();

    /**
     * Method returns object at the given index.
     *
     * @param index index of the object
     * @return object at the index
     */
    GeometricalObject getObject(int index);

    /**
     * Method adds object to the model.
     *
     * @param object element to add
     */
    void add(GeometricalObject object);

    /**
     * Method removes object from the model.
     *
     * @param object object to remove
     */
    void remove(GeometricalObject object);

    /**
     * Method for changing order of the elements.
     *
     * @param object object whose position gets changed
     * @param offset offset of the change
     */
    void changeOrder(GeometricalObject object, int offset);

    /**
     * Method returns index of the object.
     *
     * @param object object
     * @return index of the object
     */
    int indexOf(GeometricalObject object);

    /**
     * Method removes all objects from the model.
     */
    void clear();

    /**
     * Method clears modified flag.
     */
    void clearModifiedFlag();

    /**
     * Method returns true if model is modified, false otherwise.
     *
     * @return true if modified, false otherwise
     */
    boolean isModified();

    /**
     * Method adds listener.
     *
     * @param l listener to add
     */
    void addDrawingModelListener(DrawingModelListener l);

    /**
     * Method removes listener.
     *
     * @param l listener to remove
     */
    void removeDrawingModelListener(DrawingModelListener l);

    /**
     * Returns filepath of the model.
     *
     * @return filepath or null if not saved
     */
    Path getFilePath();

    /**
     * Sets filepath of the model.
     *
     * @param filePath filepath
     */
    void setFilePath(Path filePath);
}