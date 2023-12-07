package ru.practicum.main.rating.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
@Table(name = "ratings")
@IdClass(RatingCompositeKey.class)
public class Rating implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "is_positive")
    private Boolean isPositive;
    @Column(name = "initiator_id")
    private long initiatorId;

    @Override
    public int hashCode() {
        RatingCompositeKey key = new RatingCompositeKey(this.userId, this.eventId);
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rating))
            return false;
        RatingCompositeKey thisKey = new RatingCompositeKey(this.userId, this.eventId);
        Rating that = (Rating) obj;
        RatingCompositeKey thatKey = new RatingCompositeKey(that.getUserId(), that.getEventId());
        return Objects.equals(thatKey, thisKey);
    }
}
