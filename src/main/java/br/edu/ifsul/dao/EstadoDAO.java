
package br.edu.ifsul.dao;

import java.io.Serializable;
import javax.ejb.Stateful;
import modelo.Estado;

/**
 *
 * @author 20202PF.CC0003
 */
@Stateful // inicializado automaticamente pelo container
public class EstadoDAO<TIPO> extends DAOGenerico<Estado> implements Serializable {
    
    public EstadoDAO() {
        super();
        classePersistente = Estado.class;
    }
}
