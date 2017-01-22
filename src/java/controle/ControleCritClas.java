package controle;

import DAO.CritClasDAO;
import DAO.ItemDAO;
import entidade.CriteriosClassificacao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Igor
 */
@ManagedBean(name = "controleCritClas")
@ViewScoped
public class ControleCritClas {

    private CritClasDAO critClasDAO = new CritClasDAO();
    private ItemDAO itemDAO = new ItemDAO();
    
    private CriteriosClassificacao areaConhec = new CriteriosClassificacao();
    private CriteriosClassificacao assunto = new CriteriosClassificacao();
    
    private List<CriteriosClassificacao> areasConhec;
    private List<CriteriosClassificacao> assuntos;
    
    private List<String> areasConhecStr;
    private List<String> assuntosStr;

    private String areaConhecDesc = "", assuntoDesc = "";
    private String descAreaConhec, descAssunto;

    @PostConstruct
    public void init() {
        areasConhec = critClasDAO.buscaCriterios("AreaConhecimento");
        assuntos = critClasDAO.buscaCriterios("Assunto");
        //areasConhecStr = this.converteString(areasConhec);
        //assuntosStr = this.converteString(assuntos);
    }
    
    private List<String> converteString(List<CriteriosClassificacao> lista){
        List<String> listaString = new ArrayList<String>();
        
        for(CriteriosClassificacao c : lista){
            listaString.add(c.getDescCriterio());
        }
        return listaString;
    }

    public void cadastrarCriterio(String tipoCriterio) throws IOException {
        CriteriosClassificacao novo = new CriteriosClassificacao();
        switch (tipoCriterio) {
            case "AreaConhecimento":
                if (areasConhec.size() != 0) {
                    novo.setCodCriterio((critClasDAO.buscaMaiorCodigoCrit("AreaConhecimento")) + 1);
                } else {
                    novo.setCodCriterio(1);
                }
                novo.setTipoCriterio("AreaConhecimento");
                novo.setDescCriterio(descAreaConhec);
                break;
            case "Assunto":
                if (assuntos.size() != 0) {
                    novo.setCodCriterio((critClasDAO.buscaMaiorCodigoCrit("Assunto")) + 1);
                } else {
                    novo.setCodCriterio(1);
                }
                novo.setTipoCriterio("Assunto");
                novo.setDescCriterio(descAssunto);
                break;
        }
        critClasDAO.addCriterio(novo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Critério Cadastrado com Sucesso!"));
    }
    
    public String geraCodClassificador(String tipoItem){
        String preCodigo = null, codClassificador = "";
        List<String> preCodigos = new ArrayList<String>();
        List<Integer> codigos = new ArrayList<Integer>();
        String[] vtCodClassificador = new String [2];
        int maiorCodigo;
        
//        areaConhec = critClasDAO.buscaCriterioDesc(areaConhecDesc);
//        assunto = critClasDAO.buscaCriterioDesc(assuntoDesc);
        
        switch(tipoItem){
            case "Livro":
                preCodigo = String.valueOf(areaConhec.getCodCriterio()) + "L" + String.valueOf(assunto.getCodCriterio()) + "-";
                break;
            case "Academico":
                preCodigo = String.valueOf(areaConhec.getCodCriterio()) + "A" + String.valueOf(assunto.getCodCriterio()) + "-";
                break;
            case "Peridico":
                preCodigo = String.valueOf(areaConhec.getCodCriterio()) + "P" + String.valueOf(assunto.getCodCriterio()) + "-";
                break;
        }
        preCodigos = itemDAO.buscaCodClasPorPreCod(preCodigo);
        
        for(String s : preCodigos) {
            vtCodClassificador = s.split("-");
            codigos.add(Integer.parseInt(vtCodClassificador[1]));
        }
        
        maiorCodigo = this.buscaMaiorCodigo(codigos);
        
        codClassificador = preCodigo + String.valueOf(maiorCodigo + 1);
        return codClassificador;
    }
    
    private int buscaMaiorCodigo(List<Integer> codigos){
        int maior = 0;
        for (int i : codigos){
            if (i>maior){
                maior = i;
            }
        }
        return maior;
    }
    
    public List<String> getAreasConhecStr() {
        return areasConhecStr;
    }

    public void setAreasConhecStr(List<String> areasConhecStr) {
        this.areasConhecStr = areasConhecStr;
    }

    public List<String> getAssuntosStr() {
        return assuntosStr;
    }

    public void setAssuntosStr(List<String> assuntosStr) {
        this.assuntosStr = assuntosStr;
    }
    
    public String getAreaConhecDesc() {
        return areaConhecDesc;
    }

    public void setAreaConhecDesc(String areaConhecDesc) {
        this.areaConhecDesc = areaConhecDesc;
    }

    public String getAssuntoDesc() {
        return assuntoDesc;
    }

    public void setAssuntoDesc(String assuntoDesc) {
        this.assuntoDesc = assuntoDesc;
    }
    /*
    public String getAreaConhecDesc() {
        if(this.areaConhec != null){
            return this.areaConhec.getDescCriterio();
        }
        return null;
    }

    public void setAreaConhecDesc(String areaConhecDesc) {
        for(CriteriosClassificacao c : areasConhec){
            if(c.getDescCriterio() == areaConhecDesc.toString()){
                this.areaConhec = c;
            }
        }
    }

    public String getAssuntoDesc() {
        if(this.assunto != null){
            return this.assunto.getDescCriterio();
        }
        return null;
    }

    public void setAssuntoDesc(String assuntoDesc) {
        for(CriteriosClassificacao c : assuntos){
            if(c.getDescCriterio() == assuntoDesc.toString()){
                this.assunto = c;
            }
        }
    }
    */

    public CriteriosClassificacao getAreaConhec() {
        return areaConhec;
    }

    public void setAreaConhec(CriteriosClassificacao areaConhec) {
        this.areaConhec = areaConhec;
    }

    public String getDescAreaConhec() {
        return descAreaConhec;
    }

    public void setDescAreaConhec(String descAreaConhec) {
        this.descAreaConhec = descAreaConhec;
    }
    
    public List<CriteriosClassificacao> getAreasConhec() {
        return areasConhec;
    }

    public void setAreasConhec(List<CriteriosClassificacao> areasConhec) {
        this.areasConhec = areasConhec;
    }

    public List<CriteriosClassificacao> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<CriteriosClassificacao> assuntos) {
        this.assuntos = assuntos;
    }

    public CriteriosClassificacao getAssunto() {
        return assunto;
    }

    public void setAssunto(CriteriosClassificacao assunto) {
        this.assunto = assunto;
    }

    public String getDescAssunto() {
        return descAssunto;
    }

    public void setDescAssunto(String descAssunto) {
        this.descAssunto = descAssunto;
    }
    
    /**
     * <p:outputLabel value="Área de Conhecimento: "/>
                            <h:panelGrid columns="5">
                                <p:selectOneMenu id="areasConhec" value="#{controleCritClas.areaConhec}" 
                                                 converter="critClasConverter"
                                                  filter="true" filterMatchMode="startsWith">
                                    <f:selectItem itemLabel="Selecione..."/>
                                    <f:selectItems value="#{controleCritClas.areasConhec}" var="areaConhec" 
                                                   itemLabel="#{areaConhec.descCriterio}" itemValue="#{areaConhec}"/>
                                </p:selectOneMenu>
                                <p:spacer width="30"/>
                                <a href="cadastrarAreaConhecimento.xhtml" class="btn btn-info">Cadastrar</a>
                                <p:spacer width="30"/>
                                <p:commandButton value="Atualizar" onclick="self.location.reload()"/>
                            </h:panelGrid>
                            
                            <p:outputLabel value="Assunto: "/>
                            <h:panelGrid columns="5">
                                <p:selectOneMenu id="assuntos" value="#{controleCritClas.assunto}" 
                                                 converter="critClasConverter"
                                                 filter="true" filterMatchMode="startsWith">
                                    <f:selectItem itemLabel="Selecione..."/>
                                    <f:selectItems value="#{controleCritClas.assuntos}" var="assunto" 
                                                   itemLabel="#{assunto.descCriterio}" itemValue="#{assunto}"/>
                                </p:selectOneMenu>
                                <p:spacer width="30"/>
                                <a href="cadastrarAssunto.xhtml" class="btn btn-info">Cadastrar</a>
                                <p:spacer width="30"/>
                                <p:commandButton value="Atualizar" onclick="self.location.reload()"/>
                            </h:panelGrid>
     */

}
