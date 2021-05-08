package testData;

import java.util.Objects;

public class Circle {
    private double radius = 1.0;
    private String color = "red";
    private static int circleID = 0;
    private int id;

    public Circle() {
        this(23, "blue", 0);
    }

    public Circle(double radius) {
        this.radius = radius;
        this.id = circleID++;
    }

    public Circle(double radius, String color, int id){
        this.radius = radius;
        this.color = color;
        this.id = circleID++;
    }

    public double getRadius(){
        return radius;
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public Circle getCurrentObject(){
        return this;
    }

    public double calculateArea(){
        return Math.PI*Math.pow(radius, 2);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Circle circle = (Circle) obj;
        return Double.compare(circle.radius, radius) == 0 &&
                id == circle.id &&
                Objects.equals(color, circle.color);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + id;
        result = result * 31 + color.hashCode();
        result = result * 31 + (new Double(radius)).hashCode();
        return result;
    }
}
