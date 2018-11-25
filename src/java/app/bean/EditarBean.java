/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.SerieClienteREST;
import app.entity.Serie;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
        serie = this.getSerieById(String.valueOf(indexBean.serieIdSeleccionada));
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }
    
    
    
    private Serie getSerieById(String id) {
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
