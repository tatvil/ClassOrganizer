package es.tatvil.classorganizer;

import java.util.Date;

public class Profesor extends Persona {

    public Profesor(String nombre, String apellido1, String apellido2, Date fechaNacimiento, String dni, String telefono, String email, String direccion, String codigoPostal, Localidad localidad) {
        super(nombre, apellido1, apellido2, fechaNacimiento, dni, telefono, email, direccion, codigoPostal, localidad);
    }

    @Override
    public String toString() {
        return "Profesor{} " + super.toString();
    }
}

