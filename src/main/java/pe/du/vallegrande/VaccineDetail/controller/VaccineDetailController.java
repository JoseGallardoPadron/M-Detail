
package pe.du.vallegrande.VaccineDetail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.du.vallegrande.VaccineDetail.model.VaccineDetailModel;
import pe.du.vallegrande.VaccineDetail.repository.VaccineDetailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vaccines/details") // Ruta base para el controlador de detalles de vacunas
public class VaccineDetailController {

@Autowired
private VaccineDetailRepository vaccineDetailRepository;


    // Crear un nuevo detalle de vacuna
    @PostMapping("/create")
    public Mono<ResponseEntity<VaccineDetailModel>> createVaccineDetail(@RequestBody VaccineDetailModel vaccineDetail) {
        return vaccineDetailRepository.save(vaccineDetail)
                .map(savedDetail -> ResponseEntity.status(HttpStatus.CREATED).body(savedDetail))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    // Listar todos los detalles de vacunas
    @GetMapping
    public Flux<VaccineDetailModel> getAllVaccineDetails() {
        return vaccineDetailRepository.findAll();
    }

    // Obtener un detalle de vacuna por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VaccineDetailModel>> getVaccineDetailById(@PathVariable Long id) {
        return vaccineDetailRepository.findById(id)
                .map(vaccineDetail -> ResponseEntity.ok(vaccineDetail))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

// Actualizar un detalle de vacuna
@PutMapping("/{id}")
public Mono<ResponseEntity<VaccineDetailModel>> updateVaccineDetail(@PathVariable Long id, @RequestBody VaccineDetailModel vaccineDetail) {
    return vaccineDetailRepository.findById(id)
            .flatMap(existingDetail -> {
                // Actualiza los campos necesarios
                existingDetail.setVaccineId(vaccineDetail.getVaccineId());
                existingDetail.setAmountMl(vaccineDetail.getAmountMl());
                existingDetail.setDoseAmount(vaccineDetail.getDoseAmount());
                existingDetail.setPrice(vaccineDetail.getPrice());
                existingDetail.setManufacturingDate(vaccineDetail.getManufacturingDate());
                existingDetail.setExpirationDate(vaccineDetail.getExpirationDate());
                existingDetail.setStock(vaccineDetail.getStock());

                return vaccineDetailRepository.save(existingDetail);
            })
            .map(updatedDetail -> ResponseEntity.ok(updatedDetail))
            .defaultIfEmpty(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra
}



    // Eliminar (inactivar) un detalle de vacuna
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteVaccineDetail(@PathVariable Long id) {
        return vaccineDetailRepository.findById(id)
                .flatMap(existingDetail -> vaccineDetailRepository.delete(existingDetail)
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}