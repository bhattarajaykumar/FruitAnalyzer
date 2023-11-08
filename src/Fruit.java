public class Fruit {
    private String name;
    private int size;
    private String color;
    private String shape;
    private int days;

    public Fruit(String name, int size, String color, String shape, int days) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.shape = shape;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public int getDays() {
        return days;
    }
}