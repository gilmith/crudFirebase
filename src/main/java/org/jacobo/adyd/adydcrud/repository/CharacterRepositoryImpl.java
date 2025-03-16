package org.jacobo.adyd.adydcrud.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.cloud.firestore.*;
import lombok.val;
import org.jacobo.adyd.ficha.model.DataSheet;
import org.jacobo.adyd.ficha.model.Equipment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import org.jacobo.adyd.ficha.model.FireBaseClass;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CharacterRepositoryImpl implements CharacterRepository {
    private static final String CHARACTERS = "Characters";
    @Qualifier("firebasedb")
    private final Firestore fire;

    @Override
    public String createCharacter(DataSheet sheet) throws InterruptedException, ExecutionException {
        final DocumentReference docRef = fire.collection(CHARACTERS).document();
        final ApiFuture<WriteResult> result = docRef.set(sheet);
        result.get();
        return docRef.getId();
    }

    @Override
    public List<DataSheet> findByEmail(String email) throws InterruptedException, ExecutionException {
        final CollectionReference colRef = fire.collection(CHARACTERS);
        final Query query = colRef.whereEqualTo("playerEmail", email);
        final ApiFuture<QuerySnapshot> resultado = query.get();
        final QuerySnapshot querySnapshot = resultado.get();
        if (!querySnapshot.isEmpty()) {
            return querySnapshot.getDocuments().parallelStream().<DataSheet>map(mapearDocumentoAObjeto(DataSheet.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

    @Override
    public List<DataSheet> findByPlayerName(String playerName) throws InterruptedException, ExecutionException {
        final CollectionReference colRef = fire.collection(CHARACTERS);
        final Filter filter = Filter.equalTo("playerName", playerName);
        final Query query = colRef.where(filter);
        final ApiFuture<QuerySnapshot> resultado = query.get();
        final QuerySnapshot querySnapshot = resultado.get();
        if (!querySnapshot.isEmpty()) {
            return querySnapshot.getDocuments().parallelStream().<DataSheet>map(mapearDocumentoAObjeto(DataSheet.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void updateField(String characterId, String fieldName, Object value) throws InterruptedException, ExecutionException {
        final DocumentReference documentoRef = fire.collection(CHARACTERS).document(characterId);
        val actualizaciones = new HashMap<String, Object>();
        actualizaciones.put(fieldName, value);
        documentoRef.update(actualizaciones);
    }

    @Override
    public void addEquipment(String characterId, Equipment equipment) throws InterruptedException, ExecutionException {
        final DocumentReference documentoRef = fire.collection(CHARACTERS).document(characterId);
        documentoRef.update("equipment", FieldValue.arrayUnion(equipment));
    }

    @Override
    public void removeEquipment(String characterId, Equipment equipment) throws InterruptedException, ExecutionException {
        final DocumentReference documentoRef = fire.collection(CHARACTERS).document(characterId);
        documentoRef.update("equipment", FieldValue.arrayRemove(equipment));
        documentoRef.update("equipment", FieldValue.arrayUnion(equipment));
    }

    public static <T extends FireBaseClass> Function<QueryDocumentSnapshot, T> mapearDocumentoAObjeto(Class<T> clase) {
        return documento -> {
            T objeto = documento.toObject(clase);
            if (objeto != null) {
                objeto.setId(documento.getId());
            }
            return objeto;
        };
    }
}
