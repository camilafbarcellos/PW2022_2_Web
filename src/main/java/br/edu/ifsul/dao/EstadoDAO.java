
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import javax.ejb.Stateful;
import br.edu.ifsul.modelo.Estado;

/**
 *
 * @author 20202PF.CC0003
 */
@Stateful // inicializado automaticamente pelo container
public class EstadoDAO<TIPO> extends DAOGenerico<Estado> implements Serializable {
    
    public EstadoDAO() {
        super();
        classePersistente = Estado.class;
        // lista de ordenações do dao
        listaOrdem.add(new Ordem("id", "ID", "=")); // elemento 0
        listaOrdem.add(new Ordem("nome", "Nome", "like")); // elemento 1
        // definição da ordem atual
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
}
