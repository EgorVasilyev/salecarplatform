package ru.job4j.entity.car;

import ru.job4j.entity.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars_sale")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "engine", foreignKey = @ForeignKey(name = "engineId"))
    private Engine engine;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "body", foreignKey = @ForeignKey(name = "bodyId"))
    private Body body;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "year", foreignKey = @ForeignKey(name = "yearId"))
    private Year year;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color", foreignKey = @ForeignKey(name = "colorId"))
    private Color color;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "userId"))
    private User user;

    public Car(int id, String name, Engine engine, Body body, Year year, Color color, User user) {
        this.id = id;
        this.name = name;
        this.engine = engine;
        this.body = body;
        this.year = year;
        this.color = color;
        this.user = user;
    }

    public Car() {
    }

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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && Objects.equals(name, car.name)
                && Objects.equals(engine, car.engine)
                && Objects.equals(body, car.body)
                && Objects.equals(year, car.year)
                && Objects.equals(color, car.color)
                && Objects.equals(user, car.user);
    }

    public boolean equalsWithoutId(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(name, car.name)
                && Objects.equals(engine, car.engine)
                && Objects.equals(body, car.body)
                && Objects.equals(year, car.year)
                && Objects.equals(color, car.color)
                && Objects.equals(user, car.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, engine, body, year, color);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", engine=").append(engine);
        sb.append(", body=").append(body);
        sb.append(", year=").append(year);
        sb.append(", color=").append(color);
        sb.append(", user=").append(user);
        sb.append("}\n");
        return sb.toString();
    }
}
