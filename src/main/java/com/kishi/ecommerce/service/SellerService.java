package com.kishi.ecommerce.service;

import com.kishi.ecommerce.domain.AccountStatus;
import com.kishi.ecommerce.model.Seller;
import org.w3c.dom.ls.LSInput;

import java.lang.invoke.CallSite;
import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id,Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email,String otp) throws Exception;

    Seller updateSellerAccountStatus(Long sellerId,AccountStatus status) throws Exception;

}