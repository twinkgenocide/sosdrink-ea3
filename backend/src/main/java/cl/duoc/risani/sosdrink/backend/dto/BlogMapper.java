package cl.duoc.risani.sosdrink.backend.dto;

import java.time.format.DateTimeFormatter;

import cl.duoc.risani.sosdrink.backend.entities.Blog;

public class BlogMapper {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BlogDTO toDTO(Blog blog) {
        return new BlogDTO(
            blog.getId(),
            blog.getTitulo(),
            blog.getResumen(),
            blog.getContenido(),
            blog.getFecha().format(dateTimeFormatter),
            blog.getCategoriaBlog().getId(),
            blog.getCategoriaBlog().getNombre(),
            blog.getImagenUrl()
        );
    }

    public Blog fromDTO(BlogDTO blogDTO) {
        return new Blog(
            null,
            blogDTO.getTitulo(),
            blogDTO.getResumen(),
            blogDTO.getContenido(),
            null,
            blogDTO.getImagenUrl(),
            null
        );
    }
}
