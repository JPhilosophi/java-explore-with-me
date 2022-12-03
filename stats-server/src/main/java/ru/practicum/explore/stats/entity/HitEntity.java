package ru.practicum.explore.stats.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hits")
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @CreationTimestamp
    private LocalDateTime created;

    @Override
    public String toString() {
        return "HitEntity{" +
                "id=" + id +
                ", app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HitEntity)) return false;
        HitEntity hitEntity = (HitEntity) o;
        return Objects.equals(id, hitEntity.id) && Objects.equals(app, hitEntity.app) && Objects.equals(uri, hitEntity.uri) && Objects.equals(ip, hitEntity.ip) && Objects.equals(created, hitEntity.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip, created);
    }

}
