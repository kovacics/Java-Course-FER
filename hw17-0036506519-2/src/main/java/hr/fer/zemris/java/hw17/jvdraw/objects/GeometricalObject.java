package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that represents geometrical object.
 */
@SuppressWarnings("unused")
public abstract class GeometricalObject {

    private List<GeometricalObjectListener> listeners = new ArrayList<>();

    public abstract void accept(GeometricalObjectVisitor v);

    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        listeners.forEach(l -> l.geometricalObjectChanged(this));
    }
}