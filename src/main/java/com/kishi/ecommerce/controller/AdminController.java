package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.domain.AccountStatus;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller>updateSellerStatus(
            @PathVariable Long id,
            @PathVariable AccountStatus status
    ) throws Exception {
        Seller updatedSeller = sellerService.updateSellerAccountStatus(id,status);
        return ResponseEntity.ok(updatedSeller);
    }

}