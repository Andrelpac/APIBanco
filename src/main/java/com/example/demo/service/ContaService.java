package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ContaExistenteException;
import com.example.demo.exception.ContaNotException;
import com.example.demo.exception.OperacaoInexistente;
import com.example.demo.exception.SaldoInsuficienteException;
import com.example.demo.model.Conta;

@Service
public class ContaService {
	
	List<Conta> contas = new ArrayList<Conta>();
	
	public List<Conta> listarTudo(){
		return contas;
	}
	
	public Conta listarConta(String numero) throws ContaNotException {
		for (Conta conta : contas) {
			if(conta.getNumero().equals(numero)) {
				return conta;
			}
		}
		throw new ContaNotException("Conta não existe");
	}
	
	public void verificarContaExiste(Conta conta) throws ContaExistenteException {
		for (Conta oldConta : contas) {
			if(conta.getNumero().equals(oldConta.getNumero())) {
				throw new ContaExistenteException("Conta existe");
			}
		}
	}
	
	public void inserir(Conta conta) throws ContaExistenteException {
		verificarContaExiste(conta);
		contas.add(conta);
	}

	//321 654
	public Conta atualizar(Conta conta, String numero) throws ContaExistenteException, ContaNotException {
		for (Conta oldConta : contas) {
			if(oldConta.getNumero().equals(numero)) {
				if(!conta.getNumero().equals("") && conta.getNumero() != null) {
					verificarContaExiste(conta);
					oldConta.setNumero(conta.getNumero());
				}
				if(!conta.getTitular().equals("") && conta.getTitular() != null) {
					oldConta.setTitular(conta.getTitular());
				}
				return oldConta;
			}
		}
		throw new ContaNotException("Conta não existe");
	}
	
	public void deletar(String numero) throws ContaNotException {
		Conta contaDeletada = null;
		for (Conta conta : contas) {
			if(conta.getNumero().equals(numero)) {
				contaDeletada = conta;
			}
		}
		if(contaDeletada == null) {
			throw new ContaNotException("Conta não existe");
		}
		contas.remove(contaDeletada);	
	}
	
	public Conta operacao(String numero, Integer operacao, Double valor) throws SaldoInsuficienteException, ContaNotException, OperacaoInexistente {
		for (Conta conta : contas) {
			if(conta.getNumero().equals(numero)) {
				if(operacao == 1) {
					conta.credito(valor);
					return conta;
				}
				if(operacao == 2) {
					if(conta.getSaldo() < valor) {
					throw new SaldoInsuficienteException("Saldo insuficiente");
					}
					conta.debito(valor);
					return conta;
				}
				throw new OperacaoInexistente("Não existe tal operação");
			}
			
		}
		throw new ContaNotException("Conta não existe");
	}
	

}
