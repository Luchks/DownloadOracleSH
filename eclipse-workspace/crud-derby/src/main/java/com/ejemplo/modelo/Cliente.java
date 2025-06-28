package com.ejemplo.modelo;
/**
 * Cliente
 */
public class Cliente {

   private int id;
   private String code;
   private String name;
   private Persona persona;

   public Cliente(int id,String code,String name,Persona persona){
       this.id = id;
       this.code=code;
       this.name=name;
       this.persona=persona;
   }
   public void setId(int id) {
    this.id = id;
   }
   public int getId() {
    return id;
   }
   public void setCode(String code) {
    this.code = code;
   }
   public String getCode() {
    return code;
   }
   public void setName(String name) {
    this.name = name;
   }
   public String getName() {
    return name;
   }
   public void setPersona(Persona persona) {
    this.persona = persona;
   }
   public Persona getPersona() {
    return persona;
   }

}
