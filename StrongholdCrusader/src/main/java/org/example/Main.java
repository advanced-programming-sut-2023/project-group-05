package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main( String[] args )
    {
        MyHash.setMOD(1000000007);
        MyHash.setBASE(5021);
        Account test = new Account("Ariya", "AH", "prefix.suffix.aria@gmail.com", "hi", 0, 0, 0, 0);
        HashMap < String, Account > getnow = Account.getAccountsMap();
    }
}