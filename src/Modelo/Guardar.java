package Modelo;

import java.awt.Image;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Alex Reinoso
 */
@Entity
public class Guardar implements Serializable 
{
    
    @Id
    private long id;
    private String nombre;
    private Image imgscore;
    
    public Guardar(){       
    }

    public Guardar(long id, String nombre, Image imgscore) {
        this.id = id;
        this.nombre = nombre;
        this.imgscore = imgscore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Image getImgscore() {
        return imgscore;
    }

    public void setImgscore(Image imgscore) {
        this.imgscore = imgscore;
    }

    
    
    
}
