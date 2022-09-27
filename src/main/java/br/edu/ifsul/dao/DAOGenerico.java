
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 20202PF.CC0003
 */
public class DAOGenerico<TIPO> implements Serializable { // indica que necessita um tipo
    
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    @PersistenceContext(unitName = "PW2022_2_WebPU") // Payara vai instaciar de acordo com o nome da PU em persistence.xml
    protected EntityManager em; // filhos extendidos acessam os métodos diretamente
    protected Class classePersistente;
    // atributos referentes a paginação e filtro
    protected String filtro = "";
    protected List<Ordem> listaOrdem = new ArrayList<>();
    protected Ordem ordemAtual;
    protected ConverterOrdem converterOrdem;
    protected Integer maximoObjetos = 8;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;
    
    
    public DAOGenerico() {
        
    }
    
    public void primeiro() {
        posicaoAtual = 0;
    }
    
    public void anterior() {
        posicaoAtual -= maximoObjetos;
        // caso esteja na posição 0
        if (posicaoAtual < 0) {
            posicaoAtual = 0;
        }
    }
    
    public void proximo() {
        // casso haja posições posteriores para ir
        if (posicaoAtual + maximoObjetos < totalObjetos) {
            posicaoAtual += maximoObjetos;
        }
    }
    
    public void ultimo() {
        int resto = totalObjetos % maximoObjetos;
        // teste de manipulação da quantidade de páginas
        if(resto > 0) {
            posicaoAtual = totalObjetos - resto;
        } else {
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }
    
    public String getMensagemNavegacao() {
        int ate = posicaoAtual + maximoObjetos;
        if(ate > totalObjetos) {
            ate = totalObjetos;
        }
        if(totalObjetos > 0) {
            return "Listando de " + (posicaoAtual + 1) + 
                    " até " + ate + " de " + totalObjetos + " registros";
        } else {
            return "Nenhum registro encontrado...";
        }
    }

    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        filtro = filtro.replaceAll("[';-]", ""); // protegendo o filtro de SQL Injection
        if(filtro.length() > 0) {
            switch (ordemAtual.getOperador()) {
                case "=":
                    if(ordemAtual.getAtributo().equals("id")) {
                        try {
                            Integer.parseInt(filtro);
                        } catch(Exception e) {
                            filtro = "0";
                        }
                    }
                    where += " where " + ordemAtual.getAtributo() +
                            " = '" + filtro + "' ";
                    break;
                case "like":
                    where += " where upper(" + ordemAtual.getAtributo() + ") " +
                            "like '" + filtro.toUpperCase() + "%' ";
                    break;
            }
        }
        jpql += where;
        jpql += " order by " + ordemAtual.getAtributo();
        System.out.println("JQPL: " + jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() { // por enquanto está igual ao Objetos
        String jpql = "from " + classePersistente.getSimpleName()
                + " order by " + ordemAtual.getAtributo();
        return em.createQuery(jpql).getResultList();
    }

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }
    
    public void persist(TIPO obj) throws Exception { // joga a execção para a camada de cima tratar
        em.persist(obj); // o contexto transacional cuida do restante
    }
    
    public void merge(TIPO obj) throws Exception {
        em.merge(obj);
    }
    
    public TIPO getObjectByID(Object id) throws Exception { // Object é genérica para todos os tipos
        return (TIPO) em.find(classePersistente, id);
    }
    
    public void remover(TIPO obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
        /*
            as transações só existem dentro do contexto do método,
            então o objeto não está realmente associado ao EM,
            por isso primeiro o merge associa ao EM para 
            então podermos remover devidamente
        */
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistene() {
        return classePersistente;
    }

    public void setClassePersistene(Class classePersistene) {
        this.classePersistente = classePersistene;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Ordem> getListaOrdem() {
        return listaOrdem;
    }

    public void setListaOrdem(List<Ordem> listaOrdem) {
        this.listaOrdem = listaOrdem;
    }

    public Ordem getOrdemAtual() {
        return ordemAtual;
    }

    public void setOrdemAtual(Ordem ordemAtual) {
        this.ordemAtual = ordemAtual;
    }

    public ConverterOrdem getConverterOrdem() {
        return converterOrdem;
    }

    public void setConverterOrdem(ConverterOrdem converterOrdem) {
        this.converterOrdem = converterOrdem;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
}
