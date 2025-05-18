package Model;

public class Item {
    private int id;
    private String name;
    private double price;
    private String image;
    private String category;
    

    public Item() {
    }

    // Constructor with image
    public Item(int id, String name, double price, String image , String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    // Existing constructor (without image)
    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getCategory() {
    return category;
    }

    public void setCategory(String category) {
    this.category = category;
    }
    
}
