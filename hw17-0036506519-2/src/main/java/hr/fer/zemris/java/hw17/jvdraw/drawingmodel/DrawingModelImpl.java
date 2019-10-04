package hr.fer.zemris.java.hw17.jvdraw.drawingmodel;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link DrawingModel} interface.
 *
 * @author Stjepan Kovačić
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

    /**
     * List used for storing objects.
     */
    private List<GeometricalObject> objects = new ArrayList<>();

    /**
     * List of all listeners.
     */
    private List<DrawingModelListener> listeners = new ArrayList<>();

    /**
     * Filepath of the model that is saved.
     */
    private Path filePath;

    /**
     * Modification flag.
     */
    private boolean modified;


    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        object.addGeometricalObjectListener(this);
        listeners.forEach(l -> l.objectsAdded(this, objects.size() - 1, objects.size() - 1));
        modified = true;
    }

    @Override
    public void remove(GeometricalObject object) {
        int index = indexOf(object);
        if (objects.remove(object)) {
            listeners.forEach(l -> l.objectsRemoved(this, index, index));
            modified = true;
        }
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int index = indexOf(object);
        if (index + offset < 0 || index + offset > objects.size() - 1) return;
        objects.remove(object);
        objects.add(index + offset, object);
        listeners.forEach(l -> l.objectsChanged(this, index, index + offset));
        modified = true;
    }

    @Override
    public int indexOf(GeometricalObject object) {
        return objects.indexOf(object);
    }

    @Override
    public void clear() {
        objects.clear();
        modified = true;
    }

    @Override
    public void clearModifiedFlag() {
        modified = false;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void geometricalObjectChanged(GeometricalObject o) {
        int index = objects.indexOf(o);
        listeners.forEach(l -> l.objectsChanged(this, index, index));
    }

    @Override
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
