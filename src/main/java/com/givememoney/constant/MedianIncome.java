package com.givememoney.constant;

/*
기준중위소득 0% ~ 50%
기준중위소득 51% ~ 75%
기준중위소득 76% ~ 100%
기준중위소득 101% ~ 200%
기준중위소득 200% 초과
*/
public enum MedianIncome {
    UNDER_50,
    BETWEEN_51_AND_75,
    BETWEEN_76_AND_100,
    BETWEEN_101_AND_200,
    OVER_200
}
