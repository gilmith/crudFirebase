package org.jacobo.adyd.adydcrud.api;

import java.util.List;

import org.jacobo.adyd.adydcrud.model.EquipmentARD;
import org.jacobo.adyd.adydcrud.model.ResponseCreate;
import org.jacobo.adyd.adydcrud.model.UpdateBody;
import org.jacobo.adyd.ficha.model.DataSheet;
import org.jacobo.adyd.ficha.model.Equipment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping(path = "firebase")
public interface CrudApi {
	@Operation(summary = "Create a new character for Advanced Dungeons and Dragons")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Creation ok in firebase", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ResponseCreate.class)) }),
	  @ApiResponse(responseCode = "400", description = "Invalid Character supplied", 
	    content = @Content)})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCreate> createCharacter(@RequestBody DataSheet data);
	
	@GetMapping
	@Operation(summary = "Find characters created by user identified by email for Advanced Dungeons and Dragons")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK List of characteres", 
	    content = { @Content(mediaType = "application/json", 
	    		array = @ArraySchema(schema = @Schema(implementation = DataSheet.class))) }),
	  @ApiResponse(responseCode = "204", description = "Not found, empty query", 
	    content = @Content)})
	public ResponseEntity<List<DataSheet>> findByEmail(@RequestParam String email);
	
	@GetMapping(path="/playerName")
	@Operation(summary = "Find characters created by user identified by player Name for Advanced Dungeons and Dragons")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "OK List of characteres", 
	    content = { @Content(mediaType = "application/json", 
	    		array = @ArraySchema(schema = @Schema(implementation = DataSheet.class))) }),
	  @ApiResponse(responseCode = "204", description = "Not found, empty query", 
	    content = @Content)})
	public ResponseEntity<List<DataSheet>> findByPlayerName(@RequestParam String playerName);

	@PatchMapping(path="/{characterId}")
	@Operation(summary = "Update field by Name in a created character")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Acepted"),
			@ApiResponse(responseCode = "204", description = "Not found, empty query",
					content = @Content)})
	public ResponseEntity<Void> updateCharacter(@PathVariable String characterId, @RequestBody UpdateBody body);

	@PatchMapping(path="/{characterId}/equipment") // Corrección aquí
	@Operation(summary = "add, remove, replace equipment")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Acepted"),
			@ApiResponse(responseCode = "400", description = "Error in replacement")})
		public ResponseEntity<Void> updateEquipment(@PathVariable String characterId,  @RequestBody List<EquipmentARD> listEquiment);


}
