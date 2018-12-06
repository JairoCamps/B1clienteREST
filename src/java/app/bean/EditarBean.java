/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.CategoriaClienteREST;
import app.client.SerieClienteREST;
import app.entity.Categoria;
import app.entity.Serie;
import java.util.List;
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
    protected List<Categoria> listaCategorias;
    protected List<Categoria> listaCategoriasSeleccionadas;
    
    public EditarBean() {
    }
    
    @PostConstruct
    public void init(){
        serie = this.getSerieById(String.valueOf(indexBean.serieIdSeleccionada));
        listaCategorias = indexBean.getListaCategoria();
        listaCategoriasSeleccionadas = this.getCategoriasByIdSerie(String.valueOf(serie.getIdSerie()));       
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

    public List<Categoria> getListaCategoriasSeleccionadas() {
        return listaCategoriasSeleccionadas;
    }

    public void setListaCategoriasSeleccionadas(List<Categoria> listaCategoriasSeleccionadas) {
        this.listaCategoriasSeleccionadas = listaCategoriasSeleccionadas;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
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
    
    private List<Categoria> getCategoriasByIdSerie(String id){
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.findCategoriasByIdSerie_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<List<Categoria>> genericType = new GenericType<List<Categoria>>(){};
            List<Categoria> l = r.readEntity(genericType);
            return l;
        }
        return null;
    }
    
    private Categoria getCategoriaById(String id){
        CategoriaClienteREST categoriaCliente = new CategoriaClienteREST();
        Response r = categoriaCliente.find_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<Categoria> genericType = new GenericType<Categoria>(){};
            Categoria c = r.readEntity(genericType);
            return c;
        }
        return null;
    }
    
    private List<Serie> getSeriesByIdCategoria(String idCategoria) {
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.findSeriesByIdCategoria_XML(Response.class, idCategoria);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> series = r.readEntity(genericType);
            return series;
        }
        return null;
    }
    
    private void editarSerie (String id){
        SerieClienteREST serieCliente = new SerieClienteREST();
        serieCliente.edit_XML(serie, id);
    }
    
    private void editarCategoria (Categoria cat, String id){
        CategoriaClienteREST categoriaCliente = new CategoriaClienteREST();
        categoriaCliente.edit_XML(cat, id);
    }
    
    public String doEditar(){
        serie.setCategoriaList(listaCategoriasSeleccionadas);
        serie.se
        editarSerie(serie.getIdSerie().toString());
        
        for (Categoria c : listaCategoriasSeleccionadas){
            List<Serie> listaSeries = getSeriesByIdCategoria(c.getIdCategoria().toString());
            c.setSerieList(listaSeries);
            c.getSerieList().add(serie);
            editarCategoria(c, c.getIdCategoria().toString());
        }
        
        indexBean.init();
        return "index?faces-redirect=true";
    }
}
