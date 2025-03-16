package org.jacobo.adyd.adydcrud.service;

import org.jacobo.adyd.adydcrud.model.EquipmentARD;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface EquipmentService {

    public void replaceEquipmentService(String characterId, List<EquipmentARD> equipmentARDList)  throws InterruptedException, ExecutionException;
}
