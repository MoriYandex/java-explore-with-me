package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.stats.model.Stat(h.app, h.uri, count(h.ip)) " +
            "from Hit h " +
            "where h.hitDate between :start and :end " +
            "and ((:uris) is null or h.uri in :uris) " +
            "group by h.app, h.uri " +
            "order by count(h.ip) desc"
    )
    List<Stat> getStats(@Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("uris") List<String> uris);

    @Query("select new ru.practicum.stats.model.Stat(h.app, h.uri, count(distinct h.ip)) " +
            "from Hit h " +
            "where h.hitDate between :start and :end " +
            "and ((:uris) is null or h.uri in :uris) " +
            "group by h.app, h.uri " +
            "order by count(h.ip) desc"
    )
    List<Stat> getUniqueStats(@Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end,
                              @Param("uris") List<String> uris);
}
