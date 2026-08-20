package com.LoanAPI.Hero.Loan.Platform.validation;

import java.util.List;

public enum StageOrder {
    AADHAAR,
    PAN,
    CKYC,
    WORK_DATA,
    GST,
    MANDATE,
    VKYC;

    public static final List<StageOrder> SEQUENCE = List.of(values());
}