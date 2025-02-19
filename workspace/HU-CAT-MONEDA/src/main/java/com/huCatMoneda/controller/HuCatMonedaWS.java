package com.huCatMoneda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huCatMoneda.entity.HuCatMoneda;
import com.huCatMoneda.entity.HuCatMonedaId;
import com.huCatMoneda.service.IHuCatMonedaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/v1/moneda")
@CrossOrigin("*")
@Tag(name = "HuCatMoneda API", description = "API para gestionar las monedas")
public class HuCatMonedaWS {
	
	@Autowired
	private IHuCatMonedaService service;
	
	// postman: http://localhost:8010/api/v1/moneda 
	// swagger: http://localhost:8010/swagger-ui/index.html
	
	@Operation(summary = "Listar todas las monedas", description = "Retorna todas las monedas en orden ascendente por el numCia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de monedas obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay monedas disponibles")
    })
	@GetMapping
	public ResponseEntity<?> listar() {
	    try {
	        List<HuCatMoneda> monedas = service.listar();
	        if (monedas.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay monedas disponibles"); // 204 No Content
	        }
	        return ResponseEntity.ok(monedas); // 200 OK
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage()); // 500 Internal Server Error
	    }
	}

	
	@Operation(summary = "Obtener una moneda por llave compuesta", description = "Obtiene una moneda utilizando numCia y claveMoneda que juntas son la llave compuesta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Moneda encontrada"),
        @ApiResponse(responseCode = "404", description = "Moneda no encontrada")
    })
	@GetMapping("/{numCia}/{claveMoneda}")
	public ResponseEntity<?> obtenerPorId(@PathVariable int numCia, @PathVariable String claveMoneda) {
	    try {
	        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda.toUpperCase());
	        Optional<HuCatMoneda> moneda = service.obtenerPorId(id);
	        if (moneda.isPresent()) {
	            return ResponseEntity.ok(moneda.get()); // 200 OK
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Moneda no encontrada con el ID proporcionado"); // 404 Not Found
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage()); // 500 Internal Server Error
	    }
	}


	
	 @Operation(summary = "Crear una nueva moneda", description = "Registra una nueva moneda")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "Moneda creada exitosamente"),
	        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
	    })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody HuCatMoneda moneda) {
	    try {
	        // Validación: Verificar si ya existe una moneda con la misma llave compuesta
	        HuCatMonedaId id = new HuCatMonedaId(moneda.getNumCia(), moneda.getClaveMoneda().toUpperCase());
	        if (service.obtenerPorId(id).isPresent()) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una moneda con el ID proporcionado"); // 409 Conflict
	        }
	        // Guardar la nueva moneda
	        HuCatMoneda nuevaMoneda = service.guardar(moneda);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMoneda); // 201 Created
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos: " + e.getMessage()); // 400 Bad Request
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage()); // 500 Internal Server Error
	    }
	}

	
	 @Operation(summary = "Actualizar una moneda", description = "Edita una moneda existente")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Moneda actualizada exitosamente"),
	        @ApiResponse(responseCode = "404", description = "Moneda no encontrada")
	    })
	@PutMapping
	public ResponseEntity<?> editar(@RequestBody HuCatMoneda moneda) {
	    try {
	        Optional<HuCatMoneda> monedaExistente = service.obtenerPorId(new HuCatMonedaId(moneda.getNumCia(), moneda.getClaveMoneda()));
	        if (monedaExistente.isPresent()) {
	            HuCatMoneda monedaActualizada = service.guardar(moneda);
	            return ResponseEntity.ok(monedaActualizada); // 200 OK
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Moneda no encontrada para actualizar"); // 404 Not Found
	        }
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos: " + e.getMessage()); // 400 Bad Request
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage()); // 500 Internal Server Error
	    }
	}


	 
	 @Operation(summary = "Eliminar una moneda", description = "Elimina una moneda usando su llave compuesta")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "204", description = "Moneda eliminada exitosamente"),
	        @ApiResponse(responseCode = "404", description = "Moneda no encontrada")
	    })
	@DeleteMapping("/{numCia}/{claveMoneda}")
	public ResponseEntity<?> eliminar(@PathVariable int numCia, @PathVariable String claveMoneda) {
	    try {
	        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda.toUpperCase());
	        Optional<HuCatMoneda> monedaExistente = service.obtenerPorId(id);
	        if (monedaExistente.isPresent()) {
	            service.eliminar(id);
	            return ResponseEntity.noContent().build(); // 204 No Content
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Moneda no encontrada para eliminar"); // 404 Not Found
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage()); // 500 Internal Server Error
	    }
	}

}
