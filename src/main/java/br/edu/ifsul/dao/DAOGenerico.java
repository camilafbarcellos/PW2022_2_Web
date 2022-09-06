
package br.edu.ifsul.dao;

import java.io.Serializable;
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
    
    public DAOGenerico() {
        
    }

    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() { // por enquanto está igual ao Objetos
        String jpql = "from " + classePersistente.getSimpleName();
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
}
