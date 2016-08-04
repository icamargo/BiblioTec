package controle;

import DAO.ReservaDAO;
import entidade.AcademicoPrototype;
import entidade.ItemPrototype;
import entidade.LivroPrototype;
import entidade.PeriodicoPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author Igor
 */
@ManagedBean (name = "controleReserva")
@SessionScoped
public class ControleReserva {
    private List reservas;
    //private UsuarioPrototype usuario;
    private ItemPrototype item;
    private AcademicoPrototype academico;
    private LivroPrototype livro;
    private PeriodicoPrototype peridico;
    
    private Reserva novaReserva = new Reserva();
    
    private LoginControle loginControle;
    private ReservaDAO reservaDAO = new ReservaDAO();
    
    public void solicitarReserva(ItemPrototype item, UsuarioPrototype usuario) throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //usuario = loginControle.getUsuario();
        
        if(!(usuario.getSituacao().equals("Inadimplente"))){
            this.item = item;
            context.getExternalContext().redirect("reservasItem.xhtml");
            reservas = reservaDAO.getReservas(item.getNumeroCatalogo());
        }
        else{
            context.addMessage(null, new FacesMessage("Usuário Inadimplente! Regularize a situação para então solicitar uma reserva"));
        }
    }
    
    public void criarReserva(UsuarioPrototype usuario) throws IOException {
        Calendar dataRetirada = Calendar.getInstance();
        Calendar dataReserva = Calendar.getInstance();
        Calendar dataDisponibilizacao = Calendar.getInstance();
        int numeroCatalogo;

        if (reservas != null) {
            if (reservas.size() > 0){
            //pega a maior data de disponibilização e acrescenta um dia e o resultado sera a data de retirada da reserva
            dataRetirada = reservaDAO.getMaiorDataDisponibilizacao();
            dataRetirada.add(Calendar.DAY_OF_MONTH, 1);
            novaReserva.setDataRetirada(dataRetirada);
            }
            
        } else {
            dataRetirada.add(Calendar.DAY_OF_MONTH, 1);
            novaReserva.setDataRetirada(dataRetirada);
        }
        //usuario = loginControle.getUsuario();

        dataDisponibilizacao.add(Calendar.DAY_OF_MONTH, 10);

        numeroCatalogo = item.getNumeroCatalogo();

        novaReserva.setCodigoUsuario(usuario.getCodigo());
        novaReserva.setDataReserva(dataReserva);
        novaReserva.setDataDisponibilizacao(dataDisponibilizacao);
        novaReserva.setNumeroCatalogo(numeroCatalogo);
        novaReserva.setStatusReserva("Aberta");

        reservaDAO.add(novaReserva);

        DateFormat formataData = DateFormat.getDateInstance();

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("gerenciarItens.xhtml");
        context.addMessage(null, new FacesMessage("Reserva Criada com Sucesso! Data de Retirada do Item: " + formataData.format(dataRetirada.getTime())));

    }

    public List getReservas() {
        return reservas;
    }

    public void setReservas(List reservas) {
        this.reservas = reservas;
    }

//    public UsuarioPrototype getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(UsuarioPrototype usuario) {
//        this.usuario = usuario;
//    }

    public ItemPrototype getItem() {
        return item;
    }

    public void setItem(ItemPrototype item) {
        this.item = item;
    }
    
}
