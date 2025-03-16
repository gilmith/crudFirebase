package org.jacobo.adyd.adydcrud.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jacobo.adyd.adydcrud.model.EquipmentARD;
import org.jacobo.adyd.adydcrud.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquimentServiceImpl implements  EquipmentService {

    private final CharacterRepository repo;

    @Override
    public void replaceEquipmentService(String characterId, List<EquipmentARD> equipmentARDList)  throws InterruptedException, ExecutionException{
        for (EquipmentARD equiment : equipmentARDList) {
            switch (equiment.operation()) {
                case "ADD": {
                    log.debug("Add equipmento to the character {}", characterId);
                    repo.addEquipment(characterId, equiment.equipment());
                    break;
                }
                case "DELETE": {
                    log.debug("Remove equipmento to the character {}", characterId);
                    repo.removeEquipment(characterId, equiment.equipment());
                    break;
                }
                default:
                    break;
            }
        }
    }
}
