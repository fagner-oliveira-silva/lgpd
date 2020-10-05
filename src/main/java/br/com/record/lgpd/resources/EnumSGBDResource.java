package br.com.record.lgpd.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.record.lgpd.model.EnumSGBD;
import br.com.record.lgpd.repository.IEnumSGBD;

@RestController
@RequestMapping(value = "/api")
public class EnumSGBDResource {

	private TreeMap<Long, EnumSGBD> mapaDeSGBD = new TreeMap<Long, EnumSGBD>();
	private IEnumSGBD repositorio;

	public EnumSGBDResource() {
		for (EnumSGBD sgbd : EnumSGBD.values()) {
			try {
				if (!repositorio.findById(sgbd.getId()).isPresent()) {
					repositorio.save(sgbd);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
				mapaDeSGBD.put(sgbd.getId(), sgbd);
			}
		}

	}

	@GetMapping("/lgpd/tiposgbd/{id}")
	public ResponseEntity<EnumSGBD> busca(@Valid @PathVariable Long id) {
		EnumSGBD resultado = (mapaDeSGBD.containsKey(id) ? mapaDeSGBD.get(id) : null);
		if (resultado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resultado);
	}

	@GetMapping("/lgpd/tiposgbd/all")
	public ResponseEntity<List<EnumSGBD>> listaTudo() {
		List<EnumSGBD> lista = new ArrayList<EnumSGBD>();
		for (EnumSGBD sgbd : EnumSGBD.values()) {
			lista.add(sgbd);
		}
		return ResponseEntity.ok(lista);
	}

}