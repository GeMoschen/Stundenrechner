package de.gemo.stunden.units.undostates;

import de.gemo.stunden.utils.GUICreator;

public class DesignState implements UndoState {

    @Override
    public void show() {
        GUICreator.showDesignView();
    }
}
