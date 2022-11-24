package com.timejava.mata76.services;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.timejava.mata76.entidades.Servico;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

	private final static String COLECAO = "Servicos";

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
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLECAO).document("Servico_2").set(servico);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public void remover(String documentId) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLECAO).document(documentId).delete();
	}
}
