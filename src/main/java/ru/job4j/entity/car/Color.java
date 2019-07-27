package ru.job4j.entity.car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "colors_sale")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    public Color() {
    }

    public Color(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return id == color.id
                && Objects.equals(name, color.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Color{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append("}\n");
        return sb.toString();
    }
}
