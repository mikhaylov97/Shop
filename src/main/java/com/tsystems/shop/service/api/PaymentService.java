package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment createNewPayment(String type, String price);
    List<String> getPaymentTypes();
}
