package ru.practicum.explore.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "name is blank")
    private String name;
    @Email(message = "invalid email")
    @NotBlank(message = "email is blank")
    private String email;

}
