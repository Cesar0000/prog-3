package br.upe.operations;

import java.io.File;

public class BaseCRUD {
    public BaseCRUD(){
        try{
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }
    //metodo para deletar o diretorio state
//    public void deleteStateDirectory() {
//        File directory = new File(".\\state");
//        deleteRecursively(directory);
//    }
//
//    //metodo para deletar os subarquivos
//    private void deleteRecursively(File file) {
//        if (file.isDirectory()) {
//            for (File subFile : file.listFiles()) {
//                deleteRecursively(subFile);
//            }
//        }
//        file.delete();
//    }
}

