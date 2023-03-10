/**
 * 
 */
package com.gestion.empleados.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gestion.empleados.excepciones.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repositorio.EmpleadoRepositorio;

/**
 * @author maryl
 *
 */
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class EmpleadoControlador {

	@Autowired
	private EmpleadoRepositorio repositorio;
	
	//metodo para listar todos los empleados
	@GetMapping("/empleados")
	
	public List<Empleado> listarTodosLosEmpleados(){
		return repositorio.findAll();
	}
	//Este metodo sirve para guardar el empleado
	@PostMapping("/empleados")
	public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
		return repositorio.save(empleado);
		
	}
	//Este metodo sisrve para buscar un emlpeado por id
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id){
		Empleado empleado=repositorio.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No existe el empleado con el id "+id));
		return ResponseEntity.ok(empleado);	
		
	}
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado detallesEmpleado){
		Empleado empleado=repositorio.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No existe el empleado con el id "+id));
		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		empleado.setEmail(detallesEmpleado.getEmail());
		Empleado empleadoActualizado=repositorio.save(empleado);
		return ResponseEntity.ok(empleadoActualizado);	
		
	}
	@DeleteMapping("/empleados/{id}")
	public boolean eliminarEmpleado(@PathVariable Long id) {
		Empleado empleado=repositorio.findById(id).orElse(null);
		if(empleado!=null) {
			repositorio.deleteById(id);
			return true;
		}
		
		return false;
		
	}
	
	
	
}
