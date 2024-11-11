package com.kishi.ecommerce.service.impl;

import com.kishi.ecommerce.config.JwtProvider;
import com.kishi.ecommerce.domain.AccountStatus;
import com.kishi.ecommerce.domain.USER_ROLE;
import com.kishi.ecommerce.model.Address;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.repository.AddressRepository;
import com.kishi.ecommerce.repository.SellerRepository;
import com.kishi.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private  final JwtProvider jwtProvider;
    private  final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;



    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());

        if(sellerExist != null){
            throw new Exception("seller already exist, try different account");
        }

        Address savedAddress = addressRepository.save(seller.getPickupAddress());

        Seller newSeller = new Seller();

        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setPickupAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
       return sellerRepository.findById(id).orElseThrow(()->new Exception("seller not found with id"+id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){
            throw  new Exception("seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id,Seller seller) throws Exception {
        Seller existindSeller =getSellerById(id);

        if(seller.getSellerName() != null ){
            existindSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getMobile() !=null){
            existindSeller.setMobile(seller.getMobile());
        }
        if(seller.getEmail() !=null){
            existindSeller.setEmail(seller.getEmail());
        }

        if(seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName()!=null){
            existindSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }

        if(seller.getBankDetails()!=null
                && seller.getBankDetails().getAccountHolderName()!=null
                && seller.getBankDetails().getIfscCode()!=null
                && seller.getBankDetails().getAccountNumber()!=null
        ){
            existindSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existindSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            existindSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
        }

        if(seller.getPickupAddress() !=null
                && seller.getPickupAddress().getAddress()!=null
                && seller.getPickupAddress().getMobile()!=null
                && seller.getPickupAddress().getLocality()!=null
                && seller.getPickupAddress().getState()!=null
        ){
            existindSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existindSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existindSeller.getPickupAddress().setLocality(seller.getPickupAddress().getLocality());
            existindSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
        }

        if(seller.getGSTIN()!=null){
            existindSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existindSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
      Seller seller =getSellerById(id);
      sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
