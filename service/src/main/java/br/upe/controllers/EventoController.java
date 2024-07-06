package br.upe.controllers;

import br.upe.pojos.Evento;

public class EventoController {
    public static void main(String[] args){
        Evento even1 = new Evento();

        even1.setName("SUPER");

        System.out.println(even1.getName());
    }
}
