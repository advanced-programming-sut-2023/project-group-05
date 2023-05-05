package org.example;

import org.example.controller.DataBase;
import org.example.controller.Hash;
import org.example.controller.SecurityQuestions;
import org.example.model.Account;
import org.example.view.Menu;
import org.example.view.SignupLoginMenu;
import org.json.simple.JSONObject;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

class Student{
    public int id ;
    public Student(int id){
        if (id>10)
            this.id = id;
    }
    public Student(){
        System.out.println("heah");
    }
    public Student(double a){
        System.out.println("addad");
    }
}
 class Main
{
    public static void main( String[] args )
    {
        /*SecurityQuestions.initialize();
        init();
        DataBase.wakeUp();
        SignupLoginMenu.run(Menu.getScanner()) ;*/
        System.out.println(1>>>1);
    }
    public static void print(int []a){
        for(int z:a)
            System.out.println(z);
    }
    public static void f(int[] a){
        a[2] = 0;
        for (int i :a)
            i=5;
        a=new int[10];
        a[2]=1;
    }

    public static void swap(String a , String b){
        String tmp = a;
        a=b;
        b=tmp;
    }

    public static void uploadDataFromDataBase()
    {
        DataBase.wakeUp();
    }
    public static void init()
    {
        Hash.setMOD(1000000007);
        Hash.setBASE(5021);
        ArrayList < String > cap0 = new ArrayList< String >();
        ///DataBase.addNewAccount(test);

        /// testing();
    }
    public static void testing()
    {
        Account test = new Account("Ariya4", "AH", "prefix.suffix.aria@gmail.com", new Hash("hi").getHsh(), 0, "im king", 0, 0);
        JSONObject cur = DataBase.getFromDataBase("nickName", "AH");
        if(cur != null)
        {
            System.out.println(cur);
        }
    }
}