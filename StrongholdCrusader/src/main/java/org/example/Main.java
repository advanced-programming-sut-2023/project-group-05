package org.example;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main( String[] args )
    {
        Account test = new Account("Ariya", "AH", "prefix.suffix.aria@gmail.com", 0, 0, 0, 0);
        HashMap < String, Account > getnow = Account.getAccountsMap();
    }
}