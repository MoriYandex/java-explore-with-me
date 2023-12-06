package ru.practicum.main.user.model;

import lombok.*;
import ru.practicum.main.rating.model.Rating;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
@NoArgsConstructor
@ToString
@NamedEntityGraph(
        name = User.WITH_RATINGS_DATA_GRAPH,
        attributeNodes = {
                @NamedAttributeNode("ratings")
        }
)
public class User implements Serializable {
    public static final String WITH_RATINGS_DATA_GRAPH = "graph.User.ratings";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", insertable = false, nullable = false)
    @ToString.Exclude
    private Set<Rating> ratings = new HashSet<>();

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && Objects.equals(((User) obj).id, this.id);
    }
}
