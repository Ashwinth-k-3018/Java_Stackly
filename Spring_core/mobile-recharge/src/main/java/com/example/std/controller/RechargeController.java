
package com.example.std.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.std.enums.RechargeStatus;
import com.example.std.model.Recharge;
import com.example.std.service.RechargeService;

@RestController
@RequestMapping("/api/recharges")
@RequiredArgsConstructor
public class RechargeController {

    private final RechargeService rechargeService;

    // 1. Create Recharge
    
    @PostMapping
    public ResponseEntity<Recharge> createRecharge(
            @Valid @RequestBody Recharge recharge) {

        Recharge savedRecharge =
                rechargeService.createRecharge(recharge);

        return new ResponseEntity<>(
                savedRecharge, HttpStatus.CREATED);
    }

    // 2. View All Recharges
    
    @GetMapping
    public ResponseEntity<List<Recharge>> getAllRecharges() {

        return ResponseEntity.ok(
                rechargeService.getAllRecharges());
    }

    // 3. Find Recharge by ID
    
    @GetMapping("/{rechargeId}")
    public ResponseEntity<Recharge> getRechargeById(
            @PathVariable Integer rechargeId) {

        return ResponseEntity.ok(
                rechargeService.getRechargeById(rechargeId));
    }

    // 4. Update Recharge
    
    @PutMapping("/{rechargeId}")
    public ResponseEntity<Recharge> updateRecharge(
            @PathVariable Integer rechargeId,
            @Valid @RequestBody Recharge recharge) {

        return ResponseEntity.ok(
                rechargeService.updateRecharge(
                        rechargeId, recharge));
    }

    // 5. Cancel Recharge
    
    @PutMapping("/{rechargeId}/cancel")
    public ResponseEntity<String> cancelRecharge(
            @PathVariable Integer rechargeId) {

        return ResponseEntity.ok(
                rechargeService.cancelRecharge(rechargeId));
    }

    // 6. Search by Mobile Number
    
    @GetMapping("/search/mobile")
    public ResponseEntity<List<Recharge>> searchByMobileNumber(
            @RequestParam String mobileNumber) {

        return ResponseEntity.ok(
                rechargeService.searchByMobileNumber(mobileNumber));
    }

    // 7. Filter by Operator
    
    @GetMapping("/operator")
    public ResponseEntity<List<Recharge>> filterByOperator(
            @RequestParam String operator) {

        return ResponseEntity.ok(
                rechargeService.filterByOperator(operator));
    }

    // 8. Filter by Plan Type
    
    @GetMapping("/plan")
    public ResponseEntity<List<Recharge>> filterByPlanType(
            @RequestParam String planType) {

        return ResponseEntity.ok(
                rechargeService.filterByPlanType(planType));
    }

    // 9. Filter by Amount Range
    
    @GetMapping("/amount")
    public ResponseEntity<List<Recharge>> filterByAmountRange(
            @RequestParam Double minAmount,
            @RequestParam Double maxAmount) {

        return ResponseEntity.ok(
                rechargeService.filterByAmountRange(
                        minAmount, maxAmount));
    }

    // 10. Total Recharge Count
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalRechargeCount() {

        return ResponseEntity.ok(
                rechargeService.getTotalRechargeCount());
    }

    // 11. Total Successful Recharge Amount
    
    @GetMapping("/total-amount")
    public ResponseEntity<Double> getTotalRechargeAmount() {

        return ResponseEntity.ok(
                rechargeService.getTotalRechargeAmount());
    }

    // 12. Filter by Recharge Status
    
    @GetMapping("/status")
    public ResponseEntity<List<Recharge>> filterByStatus(
            @RequestParam RechargeStatus status) {

        return ResponseEntity.ok(
                rechargeService.filterByStatus(status));
    }
}
