package com.timejava.mata76.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.timejava.mata76.entidades.Usuario;
import com.timejava.mata76.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> buscarUsuarios() throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(usuarioService.buscar());
	}

	@GetMapping("/{documentId}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable String documentId) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(usuarioService.buscar(documentId));
	}

	@PostMapping("/criar-usuario")
	public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(usuarioService.salvar(usuario));
	}

	@PostMapping("/remover-usuario/{cpf}")
	public ResponseEntity<String> removerUsuario(@PathVariable String cpf){
		usuarioService.remover(cpf);
		return ResponseEntity.ok("Usuário removido!");
	}
}
