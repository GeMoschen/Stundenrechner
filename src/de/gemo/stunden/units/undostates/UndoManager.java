package de.gemo.stunden.units.undostates;

import java.util.ArrayList;
import java.util.List;

public class UndoManager {
    public static int MAX_SIZE = 10;
    public static List<UndoState> undoList = new ArrayList<UndoState>();

    public static void append(UndoState state) {
        // add
        undoList.add(state);

        // remove first element, if the queue is too long
        if (undoList.size() > MAX_SIZE) {
            undoList.remove(0);
        }
    }

    public static boolean goBack() {
        if (undoList.size() < 1) {
            return false;
        }
        undoList.get(undoList.size() - 1).show();
        undoList.remove(undoList.size() - 1);
        return true;
    }
}
