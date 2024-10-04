package br.upe.operations;

import java.io.File;

public class BaseCRUD {
    public BaseCRUD() {
        try {
            File d = new File(".\\state");
            while (!d.exists()) d.mkdirs();
        } catch (Exception e) {
        }
    }
}