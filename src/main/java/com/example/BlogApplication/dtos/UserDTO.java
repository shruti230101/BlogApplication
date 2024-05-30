package com.example.BlogApplication.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 characters.")
    private String name;

    @Email(message = "Your email address is not valid.")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be b/w 3 to 10 characters, must have one capital letter, one small letter, one digit and one special character.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")
    private String password;

    @NotEmpty
    private String about;
}
