
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
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
        // lista de ordenações do dao
        listaOrdem.add(new Ordem("id", "ID", "=")); // elemento 0
        listaOrdem.add(new Ordem("nome", "Nome", "like")); // elemento 1
        listaOrdem.add(new Ordem("estado.nome", "Estado", "like")); // elemento 2
        // definição da ordem atual
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
}
