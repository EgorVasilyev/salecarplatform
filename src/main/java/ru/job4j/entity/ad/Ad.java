package ru.job4j.entity.ad;

import ru.job4j.entity.car.Car;
import ru.job4j.entity.user.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ads_sale")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car", foreignKey = @ForeignKey(name = "carId"))
    private Car car;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "userId"))
    private User user;
    @Column(name = "closed")
    private boolean closed;
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "created")
    private Date created  = new Date();;

    public Ad(int id, String description, Car car, User user, boolean closed, byte[] picture) {
        this.id = id;
        this.description = description;
        this.car = car;
        this.user = user;
        this.closed = closed;
        this.picture = picture;
    }

    public Ad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return id == ad.id && closed == ad.closed
                && Objects.equals(description, ad.description)
                && Objects.equals(car, ad.car)
                && Objects.equals(user, ad.user)
                && Arrays.equals(picture, ad.picture)
                && Objects.equals(created, ad.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, car, user, closed, picture, created);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ad{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", car=").append(car);
        sb.append(", user=").append(user.getLogin());
        sb.append(", closed=").append(closed);
        sb.append(", created=").append(new SimpleDateFormat("d MMM yy, H:m").format(created));
        sb.append("}\n");
        return sb.toString();
    }
}
