package org.jacobo.adyd.adydcrud.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.jacobo.adyd.adydcrud.model.EquipmentARD;
import org.jacobo.adyd.adydcrud.model.ResponseCreate;
import org.jacobo.adyd.adydcrud.model.UpdateBody;
import org.jacobo.adyd.adydcrud.repository.CharacterRepository;
import org.jacobo.adyd.adydcrud.service.EquipmentService;
import org.jacobo.adyd.ficha.model.DataSheet;
import org.jacobo.adyd.ficha.model.Equipment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CrudApiImpl implements CrudApi {

	private final CharacterRepository repo;
	private final EquipmentService equipmentService;
	
	@Override
	public ResponseEntity<ResponseCreate> createCharacter(DataSheet data) {
		try {
			val response = repo.createCharacter(data);
			if(!Strings.isNullOrEmpty(response)) {
				return ResponseEntity.ok(new ResponseCreate(response, "OK", LocalDateTime.now()));
			}
		} catch (InterruptedException | ExecutionException ex) {
			log.error("Error in comunicacion with google {} creating character", ex.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseCreate(null, "KO", LocalDateTime.now()));
		}
		return null;
	}

	@Override
	public ResponseEntity<List<DataSheet>> findByEmail(String email) {
		try {
			val response = repo.findByEmail(email);
			if(response.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(response);
		} catch (InterruptedException | ExecutionException ex) {
			log.error("Error in comunicacion with google {} in findByEmail", ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@Override
	public ResponseEntity<List<DataSheet>> findByPlayerName(String playerName) {
		try {
			val response = repo.findByPlayerName(playerName);
			if(response.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(response);
		} catch (InterruptedException | ExecutionException ex){
			log.error("Error in comunicacion with google {} in findByEmail", ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@Override
	public ResponseEntity<Void> updateCharacter(String characterId, UpdateBody body) {
        try {
            repo.updateField(characterId,body.field(), body.value());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error updating character {}  field {} , error {} ", characterId, body.field(), e.getMessage());
			return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.accepted().build();
	}

	@Override
	public ResponseEntity<Void> updateEquipment(String characterId, List<EquipmentARD> listEquiment) {
        try {
            equipmentService.replaceEquipmentService(characterId, listEquiment);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.accepted().build();
	}

}
