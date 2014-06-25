package com.jeon.thomas.addcard.app;

import java.util.Calendar;

/**
 * Created by thomasjeon on 6/6/14.
 */
public class Card {

    private String mCardNumber;
    private int mExpMonth;
    private int mExpYear;
    private int mCVV;
    private int mCardType;

    private static final String VISA_REGEX = "^4\\d{0,15}";
    private static final String MASTERCARD_REGEX = "^5\\d{0,15}";
    private static final String DISCOVER_REGEX = "^6\\d{0,15}";
    private static final String AMEX_REGEX = "^3[47]\\d{0,13}";
    private static final String DINERS_CLUB_REGEX = "^3([0][05]|[6]|[8])\\d{0,14}";
    private static final String JCB_REGEX = "^(35|2131|1800)\\d{0,14}";

    public Card(String cardNumber) {
        mCardNumber = cardNumber;
    }

    public Card(String cardNumber, int expMonth, int expYear){
        mCardNumber = cardNumber;
        mExpMonth = expMonth;
        mExpYear = expYear;
    }

    public boolean isCardNumberValid() {
        //validate card number using Luhn 10

        int cardNumberLength = mCardNumber.length();
        int lengthMod = cardNumberLength%2;
        int sum = 0;

        if(cardNumberLength <12){
            return false;
        }

        for (int i = 0; i < cardNumberLength; i++) {
            int currentNum = Character.getNumericValue(mCardNumber.charAt(i));

            if (i%2 == lengthMod) {
                //double digit
                currentNum = currentNum * 2;
                if(currentNum > 9) {
                    currentNum = currentNum%10 + 1;
                }
            }
            sum += currentNum;
        }

        return sum%10 == 0;
    }

    public boolean isExpirationValid() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);

        if (mExpMonth < 1 || mExpMonth > 12) {
            return false;
        }

        if (mExpYear > currentYear) {
            return true;
        }

        if(mExpYear == currentYear && mExpMonth >= currentMonth){
            return true;
        }

        return false;
    }

    public boolean isCVVValid() {
        return mCVV > 99;
    }

    public int getCardType() {
        // 4 - Visa, 5 - MasterCard, 6 - Discover, 34,37 - Amex
        // 300,305,36,38 - Diners Club, 2131,1800,35 - JCB

        mCardType = R.string.unknown_card;

        if (mCardNumber.matches(VISA_REGEX)) {
            mCardType = R.string.visa;
        }else if(mCardNumber.matches(MASTERCARD_REGEX)) {
            mCardType = R.string.mastercard;
        }else if(mCardNumber.matches(DISCOVER_REGEX)) {
            mCardType = R.string.discover;
        }else if (mCardNumber.matches(AMEX_REGEX)){
            mCardType = R.string.amex;
        }else if (mCardNumber.matches(DINERS_CLUB_REGEX)){
            mCardType = R.string.diners_club;
        }else if (mCardNumber.matches(JCB_REGEX)) {
            mCardType = R.string.jcb;
        }

        return mCardType;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public void setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
    }

    public int getExpMonth() {
        return mExpMonth;
    }

    public void setExpMonth(int expMonth) {
        mExpMonth = expMonth;
    }

    public int getExpYear() {
        return mExpYear;
    }

    public void setExpYear(int expYear) {
        mExpYear = expYear;
    }

    public int getCVV() {
        return mCVV;
    }

    public void setCVV(int CVV) {
        mCVV = CVV;
    }
}
