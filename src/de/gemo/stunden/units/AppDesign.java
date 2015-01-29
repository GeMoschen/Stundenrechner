package de.gemo.stunden.units;


public class AppDesign {
    private final String name;
    private int foreground;
    private int background;
    private int divider;

    public AppDesign(String name, int foreground, int background) {
        this.name = name;
        this.foreground = foreground;
        this.background = background;
        this.divider = foreground;
    }
    
    public String getName() {
        return name;
    }

    public void setForeground(int foreground) {
        this.foreground = foreground;
    }

    public int getForeground() {
        return foreground;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getBackground() {
        return background;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public int getDivider() {
        return divider;
    }
}
