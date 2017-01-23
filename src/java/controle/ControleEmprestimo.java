package controle;

import DAO.EmprestimoDAO;
import DAO.ItemDAO;
import DAO.PessoaDAO;
import DAO.ReservaDAO;
import entidade.Emprestimo;
import entidade.LivroPrototype;
import entidade.Reserva;
import entidade.UsuarioPrototype;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
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
@ManagedBean (name = "controleEmprestimo")
@SessionScoped
public class ControleEmprestimo {
    
    private Emprestimo emprestimo;
    private boolean feito;
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ArrayList livrosPorCodigo = new ArrayList();
    
    private int codigoUsuario, numeroCatalogo, numeroCatalogo2, numeroCatalogo3;
    
    public void devolucao() throws IOException{
        UsuarioPrototype usuario;
        LivroPrototype livro = null, livro2 = null, livro3 = null;      
        Calendar dataDevolvido = Calendar.getInstance();
       
        usuario = pessoaDAO.getUsuarioPorCodigo(codigoUsuario);       
      
        if(numeroCatalogo!=0) livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
        if(numeroCatalogo2!=0) livro2 = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo2);
        if(numeroCatalogo3!=0) livro3 = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo3);
        
        emprestimo = emprestimoDAO.ultimoEmprestimoUsuario(usuario, livro, livro2, livro3,"Aberto");

        if(emprestimo!=null){
            if(emprestimo.getLivro()!= null){
                livro = emprestimo.getLivro();
                livro.setStatus("Disponível");
                itemDAO.atualizarItem(livro);
            }
            if(emprestimo.getLivro2()!= null){
                livro2 = emprestimo.getLivro2();
                livro2.setStatus("Disponível");
                itemDAO.atualizarItem(livro2);
            }
            if(emprestimo.getLivro3()!= null){
                livro3 = emprestimo.getLivro3();
                livro3.setStatus("Disponível");
                itemDAO.atualizarItem(livro3);
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprestimo não encontrado!"));
        }
     
        if(!((emprestimo.getDataDevPrevista().compareTo(dataDevolvido) == 1) || (emprestimo.getDataDevPrevista().compareTo(dataDevolvido) == 0))){
            int calculaMulta;
            float multaLivros = multa(emprestimo);
            calculaMulta = dataDevolvido.get(Calendar.DAY_OF_MONTH) - emprestimo.getDataDevPrevista().get(Calendar.DAY_OF_MONTH);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Atraso na entrega multa de: R$"
                                     + multaLivros * calculaMulta));
            this.insereInadimplenciaUsuario(usuario, emprestimo, emprestimo.getDataDevPrevista(), dataDevolvido, multaLivros);
        }
        
        emprestimo.setStatusEmprestimo("Fechado");
        emprestimo.setDataDevolucao(dataDevolvido);
        emprestimoDAO.atualizar(emprestimo);
        codigoUsuario = 0; numeroCatalogo = 0; numeroCatalogo2 = 0; numeroCatalogo3 = 0;
        emprestimo = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item devolvido com sucesso!"));
        
    }
    
    public float multa(Emprestimo emprestimo){
        float valor1 = 0, valor2 = 0, valor3 = 0;
        if(emprestimo.getLivro()!=null) valor1 = emprestimo.getLivro().getValorMultaDiaAtraso();
        if(emprestimo.getLivro2()!=null) valor2 = emprestimo.getLivro2().getValorMultaDiaAtraso();
        if(emprestimo.getLivro3()!=null) valor3 = emprestimo.getLivro3().getValorMultaDiaAtraso();
        return (valor1 + valor2 + valor3);
    }
    
    private void insereInadimplenciaUsuario(UsuarioPrototype usuario, Emprestimo emprestimo, Calendar dataPrevista, Calendar dataDevolucao, float multa) throws IOException {
        StringBuffer novoDetalhesInadimplencia = new StringBuffer("Usuário inadimplente por realizar devolução em atraso dos seguintes itens:\n");
        String detalhesInadimplenciaAnterior = usuario.getDetalhesInadimplencia();
        DateFormat formataData = DateFormat.getDateInstance();
        if(emprestimo.getLivro()!=null){
            novoDetalhesInadimplencia.append(String.valueOf(emprestimo.getLivro().getNumeroCatalogo()));
            novoDetalhesInadimplencia.append(" - ");
            novoDetalhesInadimplencia.append(emprestimo.getLivro().getNome()).append("\n");
        }if(emprestimo.getLivro2()!=null){
            novoDetalhesInadimplencia.append(String.valueOf(emprestimo.getLivro2().getNumeroCatalogo()));
            novoDetalhesInadimplencia.append(" - ");
            novoDetalhesInadimplencia.append(emprestimo.getLivro2().getNome()).append("\n");
        }if(emprestimo.getLivro3()!=null){
            novoDetalhesInadimplencia.append(String.valueOf(emprestimo.getLivro3().getNumeroCatalogo()));
            novoDetalhesInadimplencia.append(" - ");
            novoDetalhesInadimplencia.append(emprestimo.getLivro3().getNome()).append("\n");
        }
            novoDetalhesInadimplencia.append(".");
            novoDetalhesInadimplencia.append("\n");
            novoDetalhesInadimplencia.append("Data na qual devia ser devolvido: ");
            novoDetalhesInadimplencia.append(formataData.format(dataPrevista.getTime()));
            novoDetalhesInadimplencia.append(".");
            novoDetalhesInadimplencia.append("\n");
            novoDetalhesInadimplencia.append("Data que foi feita a devolução: ");
            novoDetalhesInadimplencia.append(formataData.format(dataDevolucao.getTime()));
            novoDetalhesInadimplencia.append(".\n");
            novoDetalhesInadimplencia.append("Valor da multa R$: ").append(multa);
            
        if (usuario.getSituacao().equals("Inadimplente")) {
            usuario.setDetalhesInadimplencia(detalhesInadimplenciaAnterior + "\n\n" + novoDetalhesInadimplencia.toString());
        }else{
            usuario.setDetalhesInadimplencia(novoDetalhesInadimplencia.toString());
        }
        usuario.setSituacao("Inadimplente");
        pessoaDAO.atualizarPessoa(usuario);
    }
 
    public void verificaReservaDoLivro(Emprestimo emprestimo, int l){
        LivroPrototype livro;
        List<Reserva> reservas;
        feito = false;
        switch (l) {
            case 0: livro = emprestimo.getLivro();
                    numeroCatalogo = livro.getNumeroCatalogo();
                    break;
            case 1: livro = emprestimo.getLivro2();
                    numeroCatalogo = livro.getNumeroCatalogo();
                    break;
            case 2: livro = emprestimo.getLivro3();
                    numeroCatalogo = livro.getNumeroCatalogo();
                    break;
        }
        
        reservas = reservaDAO.getReservas(numeroCatalogo);
        if(reservas!= null && reservas.size() > 0){
            for(Reserva res: reservas){
                if(res.getStatusReserva().equals("Aberta")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item possui reserva"));
                    feito = true;
                    break;
                }
            }
        }
    }
    
    public void renovar(Emprestimo emprestimo) throws IOException{
        Calendar dataNova;
        int livro = 0;
        
        if(!emprestimo.getStatusEmprestimo().equals("Fechado")){
            while(livro < 3){
                verificaReservaDoLivro(emprestimo, livro);
                if(feito) return;
                livro++;
            }
            if(feito){
                if (emprestimo.getQtdeRenovacoes() < 5) {
                    dataNova = emprestimo.getDataDevPrevista();
                    dataNova.add(Calendar.DAY_OF_MONTH, 5);
                    emprestimo.setDataDevPrevista(dataNova);

                    emprestimo.setQtdeRenovacoes(emprestimo.getQtdeRenovacoes() + 1);
                    emprestimoDAO.atualizar(emprestimo);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("emprestimos.xhtml");

                }else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Quantidade máxima de renovações atingidas (5)"));
            }else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Existem reservas"));
        }else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprestimo ja fechado"));
    }

    
    public void criarEmprestimo() throws IOException{
        
        String situacaoUsuario;
        LivroPrototype livro;
        UsuarioPrototype usuario;
        List<Reserva> reservas;
        livrosPorCodigo.clear();
        int numeroLivros;
        feito = true;
        Calendar dataEmprestimo = Calendar.getInstance();
        Calendar dataDevPrevista = Calendar.getInstance();
        
        
        livrosPorCodigo.add(numeroCatalogo);
        if(numeroCatalogo2 != 0) livrosPorCodigo.add(numeroCatalogo2);
        if(numeroCatalogo3 != 0) livrosPorCodigo.add(numeroCatalogo3);
        
        emprestimo = new Emprestimo();
        numeroLivros = livrosPorCodigo.size();
        usuario = pessoaDAO.getUsuarioPorCodigo(codigoUsuario);
        
        if(usuario!=null){
            situacaoUsuario = usuario.getSituacao();
            if(!(situacaoUsuario.equals("Inadimplente"))){

                for(int l = 0; l < livrosPorCodigo.size(); l++){
                    numeroCatalogo = (int) livrosPorCodigo.get(l);
                    livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
                    if(livro!=null){
                        if(livro.getStatus().equals("Disponível")){
                            reservas = reservaDAO.getReservas(numeroCatalogo);
                            if(verificaReservaDoUsuario(reservas, usuario)){
                                if(l==0) emprestimo.setLivro(livro);
                                if(l==1) emprestimo.setLivro2(livro);
                                if(l==2) emprestimo.setLivro3(livro);
                            }       
                        }else{
                            feito = false;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro indisponivel!"));
                        }
                    }else{
                        feito = false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro não encontrado!"));
                    }
                }
            }else{
                feito = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario possui restrição!"));
            }
        }else{
            feito = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario nao encontrado!"));
        }
        
        if(feito==true){
            for(int l = 0; l < livrosPorCodigo.size(); l++){
                numeroCatalogo = (int) livrosPorCodigo.get(l);
                livro = itemDAO.getLivroPorNumeroCatalogo(numeroCatalogo);
                if(livro.getStatus().equals("Disponível")){
                    livro.setStatus("Emprestado");
                    itemDAO.atualizarItem(livro);
                }
            }
            
            dataDevPrevista.add(Calendar.DAY_OF_MONTH, 10);
            emprestimo.setDataDevPrevista(dataDevPrevista);
            emprestimo.setDataEmprestimo(dataEmprestimo);
            emprestimo.setUsuario(usuario);
            emprestimo.setNumeroLivros(numeroLivros);
            emprestimo.setQtdeRenovacoes(0);
            emprestimo.setStatusEmprestimo("Aberto");
            emprestimoDAO.novoEmprestimo(emprestimo);           
            usuario.getEmprestimos().add(emprestimo);
            pessoaDAO.atualizarPessoa(usuario);
            
            codigoUsuario = 0; numeroCatalogo = 0; numeroCatalogo2 = 0; numeroCatalogo3 = 0;
            emprestimo = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprestimo realizado com sucesso"));
            
        }else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Emprestimo não realizado"));
    }
    
    public boolean verificaReservaDoUsuario(List<Reserva> reservas, UsuarioPrototype usuario){
         if(reservas!=null && reservas.size() > 0){
                for(Reserva res: reservas){
                    if(res.getStatusReserva().equals("Aberta")){
                        if(res.getCodigoUsuario() == usuario.getCodigo()){
                            res.setStatusReserva("Efetivada");
                            reservaDAO.atualizaReserva(res);                           
                            return true;
                        }
                        feito = false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item possui reserva"));
                        return false;
                    }
                }           
        }
        return true;
    }
    
    public Emprestimo buscaUltimoEmprestimo(LivroPrototype livro){
        emprestimo = new Emprestimo();
        emprestimo = emprestimoDAO.buscarUltimoEmprestimo(livro);
        
        return emprestimo;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getNumeroCatalogo() {
        return numeroCatalogo;
    }

    public void setNumeroCatalogo(int numeroCatalogo) {
        this.numeroCatalogo = numeroCatalogo;
    }
    
    public int getNumeroCatalogo2() {
        return numeroCatalogo2;
    }

    public void setNumeroCatalogo2(int numeroCatalogo2) {
        this.numeroCatalogo2 = numeroCatalogo2;
    }

    public int getNumeroCatalogo3() {
        return numeroCatalogo3;
    }

    public void setNumeroCatalogo3(int numeroCatalogo3) {
        this.numeroCatalogo3 = numeroCatalogo3;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }
}
