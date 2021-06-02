package com.om.springboot.util;

public enum ErrorConstants {
    E101(" Unable to insert the given job opportunities. "),
    E102(" Email is seems to non resource manager. "),
    E103(" Entered password is wrong. "),
    E104(" Entered OTP number is Expired."),
    E105(" Unable to generate OTP number. "),
    E106(" Entered OTP is wrong. "),
    E107("Not yet posted any opportunities. "),
    E108(" There is no job opportunities are available. "),
    E109(" Unable to update. "),
    E110(" There is no openings. ")
    ;

    private String value;

    private ErrorConstants(String value) {
        this.value = value;
    }
}
