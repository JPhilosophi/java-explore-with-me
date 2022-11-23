package ru.practicum.explore.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Category {
    private Long id;
    @NotBlank(message = "name is blank")
    private String name;

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
