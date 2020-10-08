/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.web;

import com.lunkes.sga.domain.Pessoa;
import com.lunkes.sga.services.PessoaService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ismael
 */
@Named("pessoaBean")
@RequestScoped
public class PessoaBean {
    
    @Inject
    private PessoaService pessoaService;
    
    private Pessoa pessoaSelecionada;
    
    List<Pessoa> pessoas;

    public PessoaBean() {
    }
    
    @PostConstruct
    public void inicializar(){
        //Iniciamos as variaveis
        pessoas = pessoaService.findAll();
        pessoaSelecionada = new Pessoa();
        
    }
    
    public void editListener(RowEditEvent event){
        
        Pessoa pessoa = (Pessoa) event.getObject();
        pessoaService.update(pessoa);        
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    public void salvarPessoa(){
        this.pessoaService.save(pessoaSelecionada);
        this.pessoas.add(pessoaSelecionada);
        this.pessoaSelecionada=null;
    }
     
    public void deletarPessoa(){
        this.pessoaService.delete(pessoaSelecionada);
        this.pessoas.remove(this.pessoaSelecionada);
        this.pessoaSelecionada=null;
    }
    
    public void reiniciarPessoaSelecionada(){
        this.pessoaSelecionada = new Pessoa();
    }
}
