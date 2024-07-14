package br.upe.operations;


import java.io.File;

public class SubmissionCRUD {
    public SubmissionCRUD(){
        try{
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }

}
