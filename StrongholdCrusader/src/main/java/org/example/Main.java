package org.example;

import org.example.controller.*;
import org.example.view.StartMenu;

public class Main {
    public static void main( String[] args ) throws Exception {
        DataBase.wakeUp();
        SecurityQuestions.initialize();
        StartMenu.main( args ) ;
    }
}