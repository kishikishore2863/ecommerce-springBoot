package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Seller;
import com.kishi.ecommerce.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);

}
