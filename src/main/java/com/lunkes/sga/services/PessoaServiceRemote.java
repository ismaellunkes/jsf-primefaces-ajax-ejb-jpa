/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunkes.sga.services;

import com.lunkes.sga.domain.Pessoa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ismael
 */
@Remote
public interface PessoaServiceRemote {
    
    public List<Pessoa> findAll();
    
    public Pessoa findById(Pessoa pessoa);
    
    public Pessoa findByEmail(Pessoa pessoa);
    
    public void save(Pessoa pessoa);
    
    public void update(Pessoa pessoa);
    
    public void delete(Pessoa pessoa);
            
}
