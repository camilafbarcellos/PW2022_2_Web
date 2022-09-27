
package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Cidade;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 20202PF.CC0003
 */
@Named(value = "converterCidade")
@RequestScoped
public class ConverterCidade implements Serializable, Converter {

    @PersistenceContext(unitName = "PW2022_2_WebPU")
    private EntityManager em;
    
    // da tela para o objeto
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equals("Selecione um registro")) {
            return null; // indica que não selecionou nada
        }
        return  em.find(Cidade.class, Integer.parseInt(string));
    }

    // do objeto para a tela
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        if(t == null) {
            return  null;
        }
        Cidade obj = (Cidade) t;
        return obj.getId().toString();
    }
    
}
