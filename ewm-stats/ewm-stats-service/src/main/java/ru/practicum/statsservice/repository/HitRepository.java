package ru.practicum.statsservice.repository;

import dto.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statsservice.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "select h.app as app, " +
            "h.uri as uri, " +
            "count(*) as hits " +
            "from hits h " +
            "where h.hit_date between (:start, :end) " +
            "and h.uri in (:uris) " +
            "group by h.app, h.uri", nativeQuery = true)
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String uris);

    @Query(value = "select h.app as app, " +
            "h.uri as uri, " +
            "count(*) as hits " +
            "from hits h " +
            "where h.hit_date between (:start, :end) " +
            "group by h.app, h.uri", nativeQuery = true)
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end);

    @Query(value = "select h.app as app, " +
            "h.uri as uri, " +
            "count(distinct h.ip) as hits " +
            "from hits h " +
            "where h.hit_date between (:start, :end) " +
            "and h.uri in (:uris) " +
            "group by h.app, h.uri", nativeQuery = true)
    List<ViewStats> getUniqueStats(LocalDateTime start, LocalDateTime end, String uris);

    @Query(value = "select h.app as app, " +
            "h.uri as uri, " +
            "count(distinct h.ip) as hits " +
            "from hits h " +
            "where h.hit_date between (:start, :end) " +
            "group by h.app, h.uri", nativeQuery = true)
    List<ViewStats> getUniqueStats(LocalDateTime start, LocalDateTime end);
}
