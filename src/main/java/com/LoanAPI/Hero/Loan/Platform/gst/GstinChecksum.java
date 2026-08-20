package com.LoanAPI.Hero.Loan.Platform.gst;

public class GstinChecksum {

    private static final String CODE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MOD = 36;

    public static boolean isValid(String gstin) {
        if (gstin == null || gstin.length() != 15) {
            return false;
        }

        String upper = gstin.toUpperCase();
        int factor = 2;
        int sum = 0;

        for (int i = upper.length() - 2; i >= 0; i--) {
            int codePoint = CODE_CHARS.indexOf(upper.charAt(i));
            if (codePoint == -1) {
                return false;
            }
            int product = factor * codePoint;
            sum += (product / MOD) + (product % MOD);
            factor = (factor == 2) ? 1 : 2;
        }

        int checkCodePoint = (MOD - (sum % MOD)) % MOD;
        char expectedCheckChar = CODE_CHARS.charAt(checkCodePoint);

        return expectedCheckChar == upper.charAt(upper.length() - 1);
    }
}