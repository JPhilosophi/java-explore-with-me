package ru.practicum.explore.category.entity;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        CategoryEntity categoryEntity = (CategoryEntity) obj;
        return id != null && Objects.equals(id, categoryEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
