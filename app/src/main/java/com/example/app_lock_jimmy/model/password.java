package com.example.app_lock_jimmy.model;

import android.content.Context;

import io.paperdb.Paper;

public class password {
    private String PASSWORD_KEY="PASSWORD KEY";
    public String PATTERN_SET="PATTERN";
    public String CONFIRM_PATTERN="draw the pattern again to confirm";
    public String INCORRECT_PATTERN ="Please try again";
    public String FIRST_USE="Draw an unlock pattern please";
    public String SCGEMA_FAILED="You must at least connect 4 dots!";
    public Boolean isFirst=true;

    public password(Context context){
        Paper.init(context);
    }

    public String getPASSWORD_KEY() {
//        return PASSWORD_KEY;
        return Paper.book().read(PASSWORD_KEY);
    }

    public void setPASSWORD_KEY(String PASS) {
//        this.PASSWORD_KEY = PASSWORD_KEY;
        Paper.book().write(PASSWORD_KEY,PASS);
    }


    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }
    public Boolean isCorrect(String PASS){
        return PASS.equals(getPASSWORD_KEY());
    }
}
