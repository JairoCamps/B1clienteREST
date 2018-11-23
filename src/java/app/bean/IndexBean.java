/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.SerieClienteREST;
import app.entity.Serie;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jairo
 */
@Named(value = "indexBean")
@SessionScoped
public class IndexBean implements Serializable {

    protected List<Serie> listaSeries;
    
    public IndexBean() {
    }
    
    @PostConstruct
    public void init(){
        listaSeries = getSeries();
    }

    public List<Serie> getListaSeries() {
        return listaSeries;
    }

    public void setListaSeries(List<Serie> listaSeries) {
        this.listaSeries = listaSeries;
    }
    
    public List<Serie> getSeries(){
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.findAll_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> series = r.readEntity(genericType);
            return series;
        }
        
        return null;
    }
    
}
