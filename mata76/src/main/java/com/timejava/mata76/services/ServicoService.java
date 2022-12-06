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
import com.timejava.mata76.entidades.Servico;
import com.timejava.mata76.entidades.Usuario;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

	private final static String COLECAO = "Servicos";

	public List<Servico> buscar() throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Servico> servicos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			servicos.add(document.toObject(Servico.class));
		}

		return servicos;

	}

	public Servico buscar(String documentId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLECAO).document(documentId);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		Servico servico = null;

		if(document.exists()) {
			servico = document.toObject(Servico.class);
			return servico;
		} else {
			return null;
		}
	}

	public String salvar(Servico servico) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLECAO).document("Servico_" + servico.getId()).set(servico);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public void remover(String servicoId) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		dbFirestore.collection(COLECAO).document("Servico_" + servicoId).delete();
	}
}
