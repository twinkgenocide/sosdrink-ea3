package cl.duoc.risani.sosdrink.backend.restcontrollers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.risani.sosdrink.backend.entities.Blog;
import cl.duoc.risani.sosdrink.backend.dto.BlogDTO;
import cl.duoc.risani.sosdrink.backend.dto.BlogMapper;
import cl.duoc.risani.sosdrink.backend.entities.CategoriaBlog;
import cl.duoc.risani.sosdrink.backend.services.BlogServices;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blogs")
public class BlogRestController {

    @Autowired
    private BlogServices blogServices;

    private BlogMapper blogMapper = new BlogMapper();

    // Blogs

    @PostMapping
    public ResponseEntity<BlogDTO> crearBlog(@RequestBody BlogDTO blogDto) {
        Optional<CategoriaBlog> catOptional = blogServices.obtenerCategoriaBlog(blogDto.getCategoriaBlogId());
        CategoriaBlog cat = catOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Blog blog = blogMapper.fromDTO(blogDto);
        blog.setFecha(LocalDateTime.now());
        blog.setCategoriaBlog(cat);
        
        Blog blogGuardado = blogServices.guardarBlog(blog);
        BlogDTO responseDto = blogMapper.toDTO(blogGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> listarBlogs(@RequestParam(required = false) Long categoria) {
        List<Blog> blogs;
        if (categoria != null) {
            Optional<CategoriaBlog> catOptional = blogServices.obtenerCategoriaBlog(categoria);
            CategoriaBlog cat = catOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            blogs = cat.getBlogs();
        } else {
            blogs = blogServices.listarBlogs();
        }

        List<BlogDTO> blogDTOs = blogs.stream().map(
            blog -> blogMapper.toDTO(blog)
        ).collect(Collectors.toList());

        return ResponseEntity.ok(blogDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerBlog(@PathVariable Long id) {
        Blog blog = blogServices.obtenerBlog(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BlogDTO blogDTO = blogMapper.toDTO(blog);
        return ResponseEntity.ok(blogDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> editarBlog(@PathVariable Long id, @RequestBody BlogDTO blogEditDTO) {
        Blog blog = blogServices.obtenerBlog(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CategoriaBlog categoriaBlog = blogServices
            .obtenerCategoriaBlog(blogEditDTO.getCategoriaBlogId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        blog.setTitulo(blogEditDTO.getTitulo());
        blog.setResumen(blogEditDTO.getResumen());
        blog.setImagenUrl(blogEditDTO.getImagenUrl());
        blog.setContenido(blogEditDTO.getContenido());
        blog.setCategoriaBlog(categoriaBlog);

        Blog blogGuardado = blogServices.guardarBlog(blog);
        return ResponseEntity.ok(blogMapper.toDTO(blogGuardado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBlog(@PathVariable Long id) {
        Blog blog = blogServices.obtenerBlog(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        blogServices.eliminarBlog(blog);
        return ResponseEntity.noContent().build();
    }

    // Categorías

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaBlog>> listarCategoriasBlog() {
        List<CategoriaBlog> categorias = blogServices.listarCategoriasBlog();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaBlog> crearCategoriaBlog(@RequestBody CategoriaBlog categoriaBlog) {
        CategoriaBlog nuevaCategoria = blogServices.guardarCategoriaBlog(categoriaBlog);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

}
