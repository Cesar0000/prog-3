package br.upe.operations;

import java.io.File;


public class SubscriptionCRUD {
    public SubscriptionCRUD(){
        try{
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }
}