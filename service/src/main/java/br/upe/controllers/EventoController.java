package br.upe.controllers;

import br.upe.pojos.GreatEvent;

public class EventoController {
    public static void main(String[] args){
        GreatEvent even1 = new GreatEvent();

        even1.setName("SUPER");

        System.out.println(even1.getName());
    }
}
