package converter;

import DAO.CritClasDAO;
import entidade.CriteriosClassificacao;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author igor_
 */
@FacesConverter(forClass = CriteriosClassificacao.class, value = "critClasConverter")
//@FacesConverter(forClass = CriteriosClassificacao.class)
public class CritClasConverter implements Converter, Serializable{
    private CritClasDAO critClasDAO = new CritClasDAO();
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CriteriosClassificacao retorno = null;

        if(value != null && value.trim().length() > 0) {
            retorno = this.critClasDAO.buscaCriterioId(new Integer(value));
        }

        return retorno;
    }
    
    
//    @Override
//    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
//        if (value != null && !value.isEmpty()) {
//            return (CriteriosClassificacao) uiComponent.getAttributes().get(value);
//        }
//        return null;
//    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Integer codigo = ((CriteriosClassificacao) value).getIdCriterio();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }

        return "";
    }

}