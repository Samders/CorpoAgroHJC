package com.alberthneerans.corpoagrohjc;

/**
 * Created by Alberth Neerans on 27/11/2016.
 */


public class Citas {

    private String nombre,correo,razon, id;

    public Citas(){

    }
    public Citas(String nombre, String correo, String razon, String id) {
        this.nombre = nombre;
        this.correo = correo;
        this.razon =razon;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRazon() {
        return razon;
    }

    public String getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public void setId(String id) {
        this.id = id;
    }
}
