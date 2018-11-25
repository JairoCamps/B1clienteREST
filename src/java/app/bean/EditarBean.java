/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.SerieClienteREST;
import app.entity.Serie;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jairo
 */
@Named(value = "editarBean")
@RequestScoped
public class EditarBean {

    @Inject
    private IndexBean indexBean;
    
    protected Serie serie;
    
    public EditarBean() {
    }
    
    @PostConstruct
    public void init(){
        serie = getSerieById(indexBean.serieIdSeleccionada);
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    
    
    
    private Serie getSerieById(Integer idSerie) {
        String id = Integer.toString(idSerie);
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.find_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>(){};
            Serie s = r.readEntity(genericType);
            return s;
        }
        return null;
    }
    
}
