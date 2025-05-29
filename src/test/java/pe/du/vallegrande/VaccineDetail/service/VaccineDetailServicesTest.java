package pe.du.vallegrande.VaccineDetail.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.du.vallegrande.VaccineDetail.model.VaccineDetailModel;
import pe.du.vallegrande.VaccineDetail.repository.VaccineDetailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VaccineDetailServicesTest {

    @InjectMocks
    private VaccineDetailServices vaccineDetailServices;

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

        Mono<VaccineDetailModel> response = vaccineDetailServices.createVaccineDetail(vaccineDetail);

        assertEquals(vaccineDetail, response.block());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

    @Test
    void testGetAllVaccineDetails() {
        when(vaccineDetailRepository.findAll()).thenReturn(Flux.just(vaccineDetail));

        Flux<VaccineDetailModel> response = vaccineDetailServices.getAllVaccineDetails();

        assertEquals(1, response.count().block());
        verify(vaccineDetailRepository, times(1)).findAll();
    }

    @Test
    void testGetVaccineDetailById_Success() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));

        Mono<VaccineDetailModel> response = vaccineDetailServices.getVaccineDetailById(1L);

        assertEquals(vaccineDetail, response.block());
        verify(vaccineDetailRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetVaccineDetailById_NotFound() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<VaccineDetailModel> response = vaccineDetailServices.getVaccineDetailById(1L);

        assertEquals(null, response.block()); // Verifica que el resultado sea nulo
        verify(vaccineDetailRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateVaccineDetail_Success() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));
        when(vaccineDetailRepository.save(any(VaccineDetailModel.class))).thenReturn(Mono.just(vaccineDetail));

        Mono<VaccineDetailModel> response = vaccineDetailServices.updateVaccineDetail(1L, vaccineDetail);

        assertEquals(vaccineDetail, response.block());
        verify(vaccineDetailRepository, times(1)).save(any(VaccineDetailModel.class));
    }

   @Test
void testUpdateVaccineDetail_NotFound() {
    when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

    Mono<VaccineDetailModel> response = vaccineDetailServices.updateVaccineDetail(1L, vaccineDetail);

    assertEquals(null, response.block()); // Verifica que el resultado sea nulo
    verify(vaccineDetailRepository, times(1)).findById(anyLong());
    verify(vaccineDetailRepository, never()).save(any(VaccineDetailModel.class)); // Verifica que no se llame a save
}


    @Test
    void testDeleteVaccineDetail_Success() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.just(vaccineDetail));
        when(vaccineDetailRepository.delete(any(VaccineDetailModel.class))).thenReturn(Mono.empty());

        Mono<Void> response = vaccineDetailServices.deleteVaccineDetail(1L);

        assertEquals(null, response.block()); // Verifica que el resultado sea nulo
        verify(vaccineDetailRepository, times(1)).delete(any(VaccineDetailModel.class));
    }

    @Test
    void testDeleteVaccineDetail_NotFound() {
        when(vaccineDetailRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> response = vaccineDetailServices.deleteVaccineDetail(1L);

        assertEquals(null, response.block()); // Verifica que el resultado sea nulo
        verify(vaccineDetailRepository, never()).delete(any(VaccineDetailModel.class)); // Verifica que no se llame a delete
    }
}
