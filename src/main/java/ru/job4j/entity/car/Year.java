package ru.job4j.entity.car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "years_sale")
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    private int value;

    public Year() {
    }

    public Year(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Year year = (Year) o;
        return id == year.id
                && value == year.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Year{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append("}\n");
        return sb.toString();
    }
}
