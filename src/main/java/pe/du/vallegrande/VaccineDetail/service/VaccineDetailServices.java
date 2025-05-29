package pe.du.vallegrande.VaccineDetail.service;
import org.springframework.stereotype.Service;

import pe.du.vallegrande.VaccineDetail.model.VaccineDetailModel;
import pe.du.vallegrande.VaccineDetail.repository.VaccineDetailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VaccineDetailServices {

    private VaccineDetailRepository vaccineDetailRepository;

    // Crear un nuevo detalle de vacuna
    public Mono<VaccineDetailModel> createVaccineDetail(VaccineDetailModel vaccineDetail) {
        return vaccineDetailRepository.save(vaccineDetail);
    }

    // Listar todos los detalles de vacunas
    public Flux<VaccineDetailModel> getAllVaccineDetails() {
        return vaccineDetailRepository.findAll();
    }

    // Obtener un detalle de vacuna por ID
    public Mono<VaccineDetailModel> getVaccineDetailById(Long id) {
        return vaccineDetailRepository.findById(id);
    }

   public Mono<VaccineDetailModel> updateVaccineDetail(Long id, VaccineDetailModel vaccineDetail) {
    return vaccineDetailRepository.findById(id)
        .flatMap(existingDetail -> {
            vaccineDetail.setVaccineDetailId(id); // Asegúrate de que el ID sea el correcto
            return vaccineDetailRepository.save(vaccineDetail);
        });
}


    // Eliminar un detalle de vacuna
    public Mono<Void> deleteVaccineDetail(Long id) {
        return vaccineDetailRepository.findById(id)
                .flatMap(existingDetail -> vaccineDetailRepository.delete(existingDetail));
    }
}