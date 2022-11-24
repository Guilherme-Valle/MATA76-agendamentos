package com.timejava.mata76.controller;

import java.util.concurrent.ExecutionException;

import com.timejava.mata76.entidades.Servico;
import com.timejava.mata76.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servico")
public class ServicoController {

	@Autowired
	ServicoService servicoService;

	@GetMapping("/{documentId}")
	public ResponseEntity<Servico> buscarServico(@PathVariable String documentId) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(servicoService.buscar(documentId));
	}

	@PostMapping("/criar-servico")
	public ResponseEntity<String> criarServico() throws ExecutionException, InterruptedException {
		Servico servico = new Servico(9L, "Odontologia");
		return ResponseEntity.ok(servicoService.salvar(servico));
	}
}
