package br.com.ambienteinformatica.primefaces.persistencia;


import java.util.List;

import br.com.ambienteinformatica.primefaces.entidade.Contato;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface ContatoDao extends Persistencia<Contato>{
	
	 List<Contato> consultarNomeOuTelefone(String nomeOuTelefone);
	 public void update(Contato contato);
	
	 

}
