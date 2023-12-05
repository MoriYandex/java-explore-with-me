package ru.practicum.main.compilation.model;

import lombok.*;
import ru.practicum.main.event.model.Event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@Data
@Table(name = "compilations")
@NoArgsConstructor
@ToString
@NamedEntityGraph(
        name = Compilation.WITH_EVENTS_GRAPH,
        attributeNodes = @NamedAttributeNode("events")
)
public class Compilation implements Serializable {
    public static final String WITH_EVENTS_GRAPH = "graph.Compilation.events";

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @ToString.Exclude
    private Set<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 512)
    private String title;
    @Column(nullable = false)
    private Boolean pinned;
}
