package entidade;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Igor
 */
@Entity
@Table (name = "Reservas")
public class Reserva implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idReserva;
    private int numeroCatalogo;
    private int codigoUsuario;
    private String statusReserva;
    @Temporal(TemporalType.DATE)
    private Calendar dataReserva;
    @Temporal(TemporalType.DATE)
    private Calendar dataRetirada;
    @Temporal(TemporalType.DATE)
    private Calendar dataDisponibilizacao;
    
    public Reserva(){
    }
    
    public Reserva(Calendar dataRetirada, Calendar dataDisponibilizacao, int codigoUsuario, Calendar dataReserva, int numeroCatalogo, String statusReserva){
        this.dataRetirada = dataRetirada;
        this.dataDisponibilizacao = dataDisponibilizacao;
        this.codigoUsuario = codigoUsuario;
        this.dataReserva = dataReserva;
        this.numeroCatalogo = numeroCatalogo;
        this.statusReserva = statusReserva;
    }
            
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getNumeroCatalogo() {
        return numeroCatalogo;
    }

    public void setNumeroCatalogo(int numeroCatalogo) {
        this.numeroCatalogo = numeroCatalogo;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Calendar getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Calendar dataReserva) {
        this.dataReserva = dataReserva;
    }

    public Calendar getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Calendar dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Calendar getDataDisponibilizacao() {
        return dataDisponibilizacao;
    }

    public void setDataDisponibilizacao(Calendar dataDisponibilizacao) {
        this.dataDisponibilizacao = dataDisponibilizacao;
    }
}
