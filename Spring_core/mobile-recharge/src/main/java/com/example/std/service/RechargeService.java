
package com.example.std.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.std.model.Recharge;
import com.example.std.enums.RechargeStatus;
import com.example.std.exception.RechargeNotFoundException;
import com.example.std.exception.DuplicateRechargeException;

@Service
public class RechargeService {

    private List<Recharge> recharges = new ArrayList<>();

    private Integer rechargeIdCounter = 1;

    // 1. Create Recharge
    
    public Recharge createRecharge(Recharge recharge) {

        boolean duplicate = recharges.stream()
                .anyMatch(r ->
                    r.getMobileNumber().equals(recharge.getMobileNumber())
                    && r.getOperator().equalsIgnoreCase(recharge.getOperator())
                    && r.getAmount().equals(recharge.getAmount())
                    && r.getPlanType().equalsIgnoreCase(recharge.getPlanType())
                    && r.getRechargeStatus() != RechargeStatus.CANCELLED
                );

        if (duplicate) {
            throw new DuplicateRechargeException(
                    "This recharge request already exists");
        }

        recharge.setRechargeId(rechargeIdCounter++);
        recharge.setRechargeStatus(RechargeStatus.SUCCESS);
        recharge.setRechargeDate(LocalDateTime.now());

        recharges.add(recharge);

        return recharge;
    }

    // 2. View All Recharges
    
    public List<Recharge> getAllRecharges() {
        return recharges;
    }

    // 3. Find Recharge by ID
    
    public Recharge getRechargeById(Integer rechargeId) {

        return recharges.stream()
                .filter(r -> r.getRechargeId().equals(rechargeId))
                .findFirst()
                .orElseThrow(() ->
                    new RechargeNotFoundException(
                        "Recharge not found with ID: " + rechargeId)
                );
    }

    // 4. Update Recharge
    
    public Recharge updateRecharge(
            Integer rechargeId, Recharge updatedRecharge) {

        Recharge existingRecharge = getRechargeById(rechargeId);

        boolean duplicate = recharges.stream()
                .anyMatch(r ->
                    !r.getRechargeId().equals(rechargeId)
                    && r.getMobileNumber().equals(updatedRecharge.getMobileNumber())
                    && r.getOperator().equalsIgnoreCase(updatedRecharge.getOperator())
                    && r.getAmount().equals(updatedRecharge.getAmount())
                    && r.getPlanType().equalsIgnoreCase(updatedRecharge.getPlanType())
                    && r.getRechargeStatus() != RechargeStatus.CANCELLED
                );

        if (duplicate) {
            throw new DuplicateRechargeException(
                    "This recharge request already exists");
        }

        existingRecharge.setMobileNumber(updatedRecharge.getMobileNumber());
        existingRecharge.setOperator(updatedRecharge.getOperator());
        existingRecharge.setAmount(updatedRecharge.getAmount());
        existingRecharge.setPlanType(updatedRecharge.getPlanType());
        existingRecharge.setValidityDays(updatedRecharge.getValidityDays());

        return existingRecharge;
    }

    // 5. Cancel Recharge
    
    public String cancelRecharge(Integer rechargeId) {

        Recharge recharge = getRechargeById(rechargeId);

        recharge.setRechargeStatus(RechargeStatus.CANCELLED);

        return "Recharge cancelled successfully";
    }

    // 6. Search by Mobile Number
    
    public List<Recharge> searchByMobileNumber(String mobileNumber) {

        return recharges.stream()
                .filter(r -> r.getMobileNumber().equals(mobileNumber))
                .collect(Collectors.toList());
    }

    // 7. Filter by Operator
    
    public List<Recharge> filterByOperator(String operator) {

        return recharges.stream()
                .filter(r -> r.getOperator().equalsIgnoreCase(operator))
                .collect(Collectors.toList());
    }

    // 8. Filter by Plan Type
    public List<Recharge> filterByPlanType(String planType) {

        return recharges.stream()
                .filter(r -> r.getPlanType().equalsIgnoreCase(planType))
                .collect(Collectors.toList());
    }

    // 9. Filter by Amount Range
    public List<Recharge> filterByAmountRange(
            Double minAmount, Double maxAmount) {

        return recharges.stream()
                .filter(r ->
                    r.getAmount() >= minAmount
                    && r.getAmount() <= maxAmount)
                .collect(Collectors.toList());
    }

    // 10. Total Recharge Count
    public Integer getTotalRechargeCount() {
        return recharges.size();
    }

    // 11. Total Successful Recharge Amount
    public Double getTotalRechargeAmount() {

        return recharges.stream()
                .filter(r -> r.getRechargeStatus() == RechargeStatus.SUCCESS)
                .mapToDouble(Recharge::getAmount)
                .sum();
    }

    // 12. Filter by Recharge Status
    public List<Recharge> filterByStatus(RechargeStatus status) {

        return recharges.stream()
                .filter(r -> r.getRechargeStatus() == status)
                .collect(Collectors.toList());
    }
}
