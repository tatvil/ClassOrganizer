package es.tatvil.classorganizer;

public class Localidad {
    private String nombre;
    private String provincia;
    private String codigoPostal;

    public Localidad(String nombre, String provincia, String codigoPostal) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    public Localidad() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return nombre + ", " + provincia + " (" + codigoPostal + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localidad localidad = (Localidad) o;
        return nombre.equals(localidad.nombre) && provincia.equals(localidad.provincia) && codigoPostal.equals(localidad.codigoPostal);
    }
}
