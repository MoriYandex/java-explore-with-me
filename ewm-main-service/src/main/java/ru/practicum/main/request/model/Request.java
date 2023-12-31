package ru.practicum.main.request.model;

import lombok.*;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.enums.RequestStatus;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Data
@Table(name = "requests")
@NoArgsConstructor
@ToString
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @Column
    private LocalDateTime created;

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Request && Objects.equals(((Request) obj).id, this.id);
    }
}
