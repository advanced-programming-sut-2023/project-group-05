package org.example.controller.graphicalMenuController;

import org.example.model.Account;
import org.example.view.ForgetPassword;

public class ForgetPasswordController {

    private static Account account ;

    public static void setAccount( Account account ){
        ForgetPasswordController.account = account ;
    }

    public static Account getAccount(){
        return ForgetPasswordController.account ;
    }

}
