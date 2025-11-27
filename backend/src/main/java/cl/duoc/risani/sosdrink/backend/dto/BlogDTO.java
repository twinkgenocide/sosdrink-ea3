package cl.duoc.risani.sosdrink.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
	private Long id;
	private String titulo;
	private String resumen;
	private String contenido;
	private String fecha;
	private Long categoriaBlogId;
	private String categoriaBlogNombre;
    private String imagenUrl;
}
