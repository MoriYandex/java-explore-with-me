package ru.main.request.model;

import lombok.*;
import ru.main.request.enums.RequestStatus;
import ru.main.user.model.User;
import ru.main.event.model.Event;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
}
