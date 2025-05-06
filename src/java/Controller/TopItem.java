package Controller;

import Model.*;

public class TopItem {
    private final String label;
    private final int value;

    public TopItem(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public int getValue() { return value; }
}
