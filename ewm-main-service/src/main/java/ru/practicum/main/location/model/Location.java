package ru.practicum.main.location.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Data
@Table(name = "locations")
@NoArgsConstructor
@ToString
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Float lat;
    @Column(nullable = false, unique = true)
    private Float lon;

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Location && Objects.equals(((Location) obj).id, this.id);
    }
}