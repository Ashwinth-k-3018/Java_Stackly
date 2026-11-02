
package com.example.std.model;

import java.time.LocalDateTime;

import com.example.std.enums.RechargeStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {

    private Integer rechargeId;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "[0-9]{10}",
             message = "Mobile number must contain 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Operator is required")
    private String operator;

    @NotNull(message = "Recharge amount is required")
    @DecimalMin(value = "10.0",
                message = "Minimum recharge amount is 10")
    private Double amount;

    @NotBlank(message = "Plan type is required")
    private String planType;

    @NotNull(message = "Validity days are required")
    @Min(value = 1, message = "Validity must be at least 1 day")
    private Integer validityDays;

    private RechargeStatus rechargeStatus;
    
    private LocalDateTime rechargeDate;
}
