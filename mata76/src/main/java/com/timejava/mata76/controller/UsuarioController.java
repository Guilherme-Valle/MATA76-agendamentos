package com.timejava.mata76.controller;

import java.util.concurrent.ExecutionException;

import com.timejava.mata76.entidades.Usuario;
import com.timejava.mata76.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/{documentId}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable String documentId) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(usuarioService.buscar(documentId));
	}

	@PostMapping("/criar-usuario")
	public ResponseEntity<String> criarUsuario() throws ExecutionException, InterruptedException {
		Usuario usuario = new Usuario("99999999997", "Renato");
		return ResponseEntity.ok(usuarioService.salvar(usuario));
	}
}
