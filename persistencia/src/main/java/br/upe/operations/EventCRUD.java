package br.upe.operations;
import  br.upe.pojos.*;
import java.io.File;

import java.io.File;

public class EventCRUD {
    public EventCRUD(){
        try{
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }

}
