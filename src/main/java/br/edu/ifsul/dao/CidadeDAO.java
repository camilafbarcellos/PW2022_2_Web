
package br.edu.ifsul.dao;

import java.io.Serializable;
import javax.ejb.Stateful;
import br.edu.ifsul.modelo.Cidade;

/**
 *
 * @author 20202PF.CC0003
 */
@Stateful // inicializado automaticamente pelo container
public class CidadeDAO<TIPO> extends DAOGenerico<Cidade> implements Serializable {
    
    public CidadeDAO() {
        super();
        classePersistente = Cidade.class;
    }
}
