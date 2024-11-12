package com.kishi.ecommerce.controller;

import com.kishi.ecommerce.domain.AccountStatus;
import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.model.VerificationCode;
import com.kishi.ecommerce.repository.SellerRepository;
import com.kishi.ecommerce.repository.VerificationCodeRepository;
import com.kishi.ecommerce.request.LoginRequest;
import com.kishi.ecommerce.response.AuthResponse;
import com.kishi.ecommerce.service.AuthService;
import com.kishi.ecommerce.service.EmailService;
import com.kishi.ecommerce.service.SellerService;
import com.kishi.ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final SellerRepository sellerRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<AuthResponse>loginSeller(@RequestBody LoginRequest req) throws Exception {
        String otp = req.getOtp();
        String email = req.getEmail();
//        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
//        if(verificationCode == null ||  !verificationCode.getOtp().equals(req.getOtp()) ){
//            throw new Exception("worng otp");
//        }
        req.setEmail("seller_"+email);
        AuthResponse authResponse =authService.siging(req);
        return  ResponseEntity.ok(authResponse);
    }




    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp)throws Exception{

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if( verificationCode ==null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(),otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

//    @PostMapping
//    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception{
//        Seller savedSeller = sellerService.createSeller(seller);
//
//
//        String otp= OtpUtil.generateOtp();
//        VerificationCode verificationCode = new VerificationCode();
//        verificationCode.setOtp(otp);
//        verificationCode.setEmail(seller.getEmail());
//        verificationCodeRepository.save(verificationCode);
//
//        String subject ="kishi's Shop Email Verification Code";
//        String text = "Welcome to kishi's shop , verify your account using this link";
//        String frontend_url = "";
//
//        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject,text+frontend_url);
////        if(emailService !=null){
////
////        }
//        return new ResponseEntity<>(savedSeller,HttpStatus.OK);
//
//    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "kishi's Shop Email Verification Code";
        String text = "Welcome to kishi's shop , verify your account using this link"+otp;
        String frontend_url = "";

        if (emailService != null) {
            emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
        } else {
            throw new Exception("djcbjdsbcbsdckb");
        }

        return new ResponseEntity<>(savedSeller, HttpStatus.OK);
    }




    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id)throws Exception{
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt)throws Exception{
        Seller seller = sellerService.getSellerProfile(jwt);
        return  new ResponseEntity<>(seller, HttpStatus.OK);
    }


//    public ResponseEntity<SellerReport> getSellerReport(
//            @RequestHeader("Authorization") String jwt)throws Exception{
//        Seller seller = sellerService.getSellerProfile(jwt);
//        SellerReport report = seller
//
//    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false)AccountStatus status){
        if(status == null){
            List<Seller> sellers =sellerRepository.findAll();
            return  ResponseEntity.ok(sellers);
        }
        List<Seller> sellers =sellerService.getAllSellers(status);
        return  ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updatesSeller(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Seller seller
    )throws Exception{

        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception{
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
