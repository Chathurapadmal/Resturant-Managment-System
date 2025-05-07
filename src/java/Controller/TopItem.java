package Controller;

public class TopItem {
    private String name;      // for item name or date
    private int totalSold;    // for quantity or daily total

    public TopItem(String name, int totalSold) {
        this.name = name;
        this.totalSold = totalSold;
    }


    public int getTotalSold() {
        return totalSold;
    }

    public String getName() {
        return name;
    }
}
