package hr.fer.zemris.java.hw17.jvdraw.list;

import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

import javax.swing.*;

/**
 * Class represents list model used for storing drawing objects.
 *
 * @author Stjepan Kovačić
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    private static final long serialVersionUID = 4837105765248520273L;

    /**
     * Reference to the drawing model.
     */
    private DrawingModel model;

    /**
     * Constructs list model adapter of the specified drawing model.
     *
     * @param model drawing model (adaptee)
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = model;
        model.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return model.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }

    /**
     * Method removes elements from the list model.
     *
     * @param o element to be removed
     */
    public void removeElement(GeometricalObject o) {
        model.remove(o);
    }

    /**
     * Method change position of the given object for the given offset.
     *
     * @param object object
     * @param offset offset
     */
    public void changeOrder(GeometricalObject object, int offset) {
        model.changeOrder(object, offset);
    }


    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(source, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(source, index0, index1);
    }
}
