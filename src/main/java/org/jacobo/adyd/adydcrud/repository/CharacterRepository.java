package org.jacobo.adyd.adydcrud.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.jacobo.adyd.ficha.model.DataSheet;
import org.jacobo.adyd.ficha.model.Equipment;

public interface CharacterRepository {
	
	public String createCharacter(DataSheet sheet) throws InterruptedException, ExecutionException;
	public List<DataSheet> findByEmail(String email) throws InterruptedException, ExecutionException;
	public List<DataSheet> findByPlayerName(String playerName)  throws InterruptedException, ExecutionException;
	public void updateField(String characterId, String fieldName, Object value) throws InterruptedException, ExecutionException;
	public void addEquipment(String characterId, Equipment equipment)  throws InterruptedException, ExecutionException;
	public void removeEquipment(String characterId, Equipment equipment)  throws InterruptedException, ExecutionException;
}
