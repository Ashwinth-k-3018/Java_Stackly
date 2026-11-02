package com.example.std.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer studentId;

    @NotBlank(message = "Student name cannot be empty")
    @Size(min = 2, max = 50,
          message = "Student name must be between 2 and 50 characters")
    private String studentName;

    @NotBlank(message = "Course name cannot be empty")
    private String courseName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age cannot exceed 60")
    private Integer age;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$",
             message = "Phone number must contain exactly 10 digits")
    private String phone;

    @NotBlank(message = "Gender cannot be empty")
    @Pattern(regexp = "(?i)^(Male|Female|Other)$",
             message = "Gender must be Male, Female or Other")
    private String gender;
}
