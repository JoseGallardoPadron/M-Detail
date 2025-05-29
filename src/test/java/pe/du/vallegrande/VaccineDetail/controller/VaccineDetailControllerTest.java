package pe.du.vallegrande.VaccineDetail.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.du.vallegrande.VaccineDetail.model.VaccineDetailModel;
import pe.du.vallegrande.VaccineDetail.repository.VaccineDetailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VaccineDetailControllerTest {

    @InjectMocks
    private VaccineDetailController vaccineDetailController;

    @Mock
    private VaccineDetailRepository vaccineDetailRepository;

    private VaccineDetailModel vaccineDetail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vaccineDetail = new VaccineDetailModel();
        vaccineDetail.setVaccineDetailId(1L);
        // Configura otros atributos de vaccineDetail según sea necesario
    }

    @Test
    void testCreateVaccineDetail_Success() {
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(vaccineDetail));

        Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.createVaccineDetail(vaccineDetail);

        assertEquals(HttpStatus.CREATED, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

    @Test
    void testCreateVaccineDetail_Failure() {
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.createVaccineDetail(vaccineDetail);

        assertEquals(HttpStatus.BAD_REQUEST, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

    @Test
    void testGetAllVaccineDetails() {
        when(vaccineDetailRepository.findAll()).thenReturn(Flux.just(vaccineDetail));

        Flux<VaccineDetailModel> response = vaccineDetailController.getAllVaccineDetails();

        assertEquals(1, response.count().block());
        verify(vaccineDetailRepository, times(1)).findAll();
    }

    @Test
    void testGetVaccineDetailById_Success() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));

        Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.getVaccineDetailById(1L);

        assertEquals(HttpStatus.OK, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetVaccineDetailById_NotFound() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.getVaccineDetailById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateVaccineDetail_Success() {
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(vaccineDetail));
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));

        Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.updateVaccineDetail(1L, vaccineDetail);

        assertEquals(HttpStatus.OK, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

@Test
void testUpdateVaccineDetail_NotFound() {
    // Simular que no se encuentra el detalle de la vacuna
    when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

    // Llama al método que estás probando
    Mono<ResponseEntity<VaccineDetailModel>> response = vaccineDetailController.updateVaccineDetail(1L, new VaccineDetailModel());

    // Verifica que se devuelve un NOT_FOUND
    assertEquals(HttpStatus.NOT_FOUND, response.block().getStatusCode());

    // Verificar que save no fue llamado
    verify(vaccineDetailRepository, never()).save(any(VaccineDetailModel.class));
    verify(vaccineDetailRepository, times(1)).findById(anyLong());
}




    @Test
    void testDeleteVaccineDetail_Success() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));
        when(vaccineDetailRepository.delete(any(VaccineDetailModel.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> response = vaccineDetailController.deleteVaccineDetail(1L);

        assertEquals(HttpStatus.OK, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).delete(any(VaccineDetailModel.class));
    }

    @Test
    void testDeleteVaccineDetail_NotFound() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> response = vaccineDetailController.deleteVaccineDetail(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.block().getStatusCode());
        verify(vaccineDetailRepository, times(1)).findById(anyLong());
    }
}
