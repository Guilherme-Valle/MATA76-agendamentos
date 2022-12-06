package com.timejava.mata76.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.timejava.mata76.entidades.Agendamento;
import com.timejava.mata76.entidades.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	private final static String COLECAO = "Usuarios";

	public List<Usuario> buscar() throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Usuario> usuarios = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			usuarios.add(document.toObject(Usuario.class));
		}

		return usuarios;
	}

	public Usuario buscar(String documentId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLECAO).document(documentId);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		Usuario usuario = null;

		if(document.exists()) {
			usuario = document.toObject(Usuario.class);
			return usuario;
		} else {
			return null;
		}
	}

	public String salvar(Usuario usuario) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLECAO).document("Usuario_" + usuario.getCpf()).set(usuario);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public void remover(String cpf) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		 dbFirestore.collection(COLECAO).document("Usuario_" + cpf).delete();
	}
}
