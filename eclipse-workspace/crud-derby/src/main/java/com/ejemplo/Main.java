package com.ejemplo;

import com.ejemplo.ui.PersonaApp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("funca please|");
        SwingUtilities.invokeLater(PersonaApp::new);
        System.out.println("funca please|");


    }
    class Cliente{
        private String name;
        private int id;
        Cliente(String name,int id){
            this.name=name;
            this.id=id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
