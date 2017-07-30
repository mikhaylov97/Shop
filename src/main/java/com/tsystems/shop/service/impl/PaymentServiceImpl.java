package com.tsystems.shop.service.impl;


import com.tsystems.shop.dao.api.PaymentDao;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.enums.PaymentStatusEnum;
import com.tsystems.shop.model.enums.PaymentTypeEnum;
import com.tsystems.shop.service.api.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentDao paymentDao;

    @Override
    public Payment createNewPayment(String type, String price) {
        Payment payment = new Payment(type, price, PaymentStatusEnum.AWAITING_PAYMENT.name());
        return payment;
    }

    @Override
    public List<String> getPaymentTypes() {
        List<String> types = new ArrayList<>();
        types.add(PaymentTypeEnum.CASH.name());
        types.add(PaymentTypeEnum.CREDIT.name());
        return types;
    }
}
