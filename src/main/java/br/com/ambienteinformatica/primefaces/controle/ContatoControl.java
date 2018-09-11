package br.com.ambienteinformatica.primefaces.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.userdetails.ldap.Person;
import org.springframework.stereotype.Controller;

import br.com.ambienteinformatica.primefaces.entidade.Contato;
import br.com.ambienteinformatica.primefaces.persistencia.ContatoDao;
import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;

@Controller("ContatoControl")
@Scope("conversation")
public class ContatoControl {

	private Contato contato = new Contato();

	@Autowired
	private ContatoDao contatoDao;

	private List<Contato> contatos = new ArrayList<Contato>();

	private String nomeOuTelefone;

	private String telefone;

	private Contato contatoSelecionado;

	public ContatoControl() {

		contatoSelecionado = contato;
	}

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			if (contato.getNome().isEmpty() && contato.getTelefone().isEmpty()) {
				UtilFaces.addMensagemFaces("Campo Nome e telefone estão vazios.");
			} else {

				contatoDao.incluir(contato);
				listar(evt);
				contato = new Contato();
				UtilFaces.addMensagemFaces("Cantato Salvo com sucesso.");
			}

		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Por favor clique em Salvar Alterações.");
		}

	}

	public void listar(ActionEvent evt) {
		try {
			contatos = contatoDao.listar();
			contato = new Contato();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void pesquisar() {
		contatos = contatoDao.consultarNomeOuTelefone(nomeOuTelefone);
	}

	public void excluir(Contato contato) throws PersistenciaException {

		try {
			contatoDao.excluirPorId(contato.getId());
			contatos = contatoDao.listar();
			UtilFaces.addMensagemFaces("Cantato excluido com sucesso.");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}

	}

	public void update(ActionEvent evt) {
		try {
			contato = contatoSelecionado;
			contatoDao.alterar(contato);
			listar(evt);
			contato = new Contato();
			UtilFaces.addMensagemFaces("Cantato Salvo com sucesso.");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void alterar(ActionEvent evt) {
		try {
			contatoDao.alterar(contato);
			listar(evt);
			contato = new Contato();
			UtilFaces.addMensagemFaces("Cantato Salvo com sucesso.");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void selecionarContatoParaEdicao(Contato contato) {

		try {
			this.contato = contatoDao.consultar(contato.getId());
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void modificar(Contato contato) throws PersistenciaException {
		contatoDao.alterar(contato);
	}

	public void selecionarContato(Contato contato) {
		try {

			contatoSelecionado = contato;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao obter o contato Selecionado");
		}

	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public String getnomeOuTelefone() {
		return nomeOuTelefone;
	}

	public void setnomeOuTelefone(String nomeOuTelefone) {
		this.nomeOuTelefone = nomeOuTelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contato == null) ? 0 : contato.hashCode());
		result = prime * result + ((contatoDao == null) ? 0 : contatoDao.hashCode());
		result = prime * result + ((contatos == null) ? 0 : contatos.hashCode());
		result = prime * result + ((nomeOuTelefone == null) ? 0 : nomeOuTelefone.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContatoControl other = (ContatoControl) obj;
		if (contato == null) {
			if (other.contato != null)
				return false;
		} else if (!contato.equals(other.contato))
			return false;
		if (contatoDao == null) {
			if (other.contatoDao != null)
				return false;
		} else if (!contatoDao.equals(other.contatoDao))
			return false;
		if (contatos == null) {
			if (other.contatos != null)
				return false;
		} else if (!contatos.equals(other.contatos))
			return false;
		if (nomeOuTelefone == null) {
			if (other.nomeOuTelefone != null)
				return false;
		} else if (!nomeOuTelefone.equals(other.nomeOuTelefone))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContatoControl [contato=" + contato + ", contatoDao=" + contatoDao + ", contatos=" + contatos
				+ ", nome=" + nomeOuTelefone + ", telefone=" + telefone + "]";
	}

	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

}
