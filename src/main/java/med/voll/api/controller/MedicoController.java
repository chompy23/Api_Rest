package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoReporitory medicoReporitory;


    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
        System.out.println("El request llego correctamente");
        medicoReporitory.save(new Medico(datosRegistroMedico));
    }

    @GetMapping
    public Page<DatosListadoMedicos> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
//        return medicoReporitory.findAll(paginacion).map(DatosListadoMedicos::new);
        return medicoReporitory.findByActivoTrue(paginacion).map(DatosListadoMedicos::new);
    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoReporitory.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminar(@PathVariable Long id) {
        Medico medico = medicoReporitory.getReferenceById(id);
        medico.desactivarMedico();
    }

//    Delete de registro medico completo
//    public void eliminar(@PathVariable Long id) {
//        Medico medico = medicoReporitory.getReferenceById(id);
//        medicoReporitory.delete(medico);
//    }
}