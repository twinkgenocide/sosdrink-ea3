package cl.duoc.risani.sosdrink.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.repository.UsuarioRepositories;

/**
 * Pruebas unitarias para la clase UsuarioServicesImpl.
 * Se utiliza Mockito para simular el comportamiento del repositorio.
 */
@ExtendWith(MockitoExtension.class) // Habilita la inyección de mocks de Mockito
class UsuarioServicesImplTest {

    // @Mock: Crea una simulación (mock) del repositorio.
    // Esta instancia no se conectará a la base de datos.
    @Mock
    private UsuarioRepositories usuarioRepositories;

    @Mock
    private PasswordEncoder passwordEncoder;

    // @InjectMocks: Crea una instancia de UsuarioServicesImpl e inyecta
    // automáticamente el mock de 'usuarioRepositories' en ella.
    @InjectMocks
    private UsuarioServicesImpl usuarioServices;

    // Objeto de prueba que usaremos en varios tests
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configura un usuario de prueba antes de cada test
        usuario = new Usuario();
        usuario.setId(1L);  // Usando Long para el ID
        usuario.setCorreo("usuario@prueba.sb");
        usuario.setNombre("Usuario de Prueba");
        // Asigna otros campos si son necesarios para las pruebas
    }

    @Test
    void testCrearUsuario() {
        // 1. Arrange (Configurar)
        // Cuando se llame a usuarioRepositories.save() con CUALQUIER Usuario...
        when(usuarioRepositories.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioRepositories.existsByCorreo(any())).thenReturn(false);

        // 2. Act (Actuar)
        // Llama al método del servicio que queremos probar
        Usuario usuarioGuardado = usuarioServices.crear(usuario);

        // 3. Assert (Verificar)
        // Comprueba que el resultado no es nulo y tiene los datos esperados
        assertNotNull(usuarioGuardado);
        assertEquals(1L, usuarioGuardado.getId());  // Verifica el ID numérico
        assertEquals("Usuario de Prueba", usuarioGuardado.getNombre());

        // Verifica que el método save() del repositorio fue llamado exactamente 1 vez
        verify(usuarioRepositories, times(1)).save(any(Usuario.class));
    }

    @Test
    void testObtenerId_Encontrado() {
        // 1. Arrange
        // Simula que el repositorio SÍ encuentra el usuario con ID 1
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario));

        // 2. Act
        Usuario usuarioEncontrado = usuarioServices.obtenerId(1L);

        // 3. Assert
        assertNotNull(usuarioEncontrado);
        assertEquals(1L, usuarioEncontrado.getId());
        verify(usuarioRepositories, times(1)).findById(1L);
    }

    @Test
    void testObtenerId_NoEncontrado() {
        // 1. Arrange
        // Simula que el repositorio NO encuentra el usuario (devuelve Optional vacío)
        when(usuarioRepositories.findById(99L)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        // Verifica que se lanza una RuntimeException cuando se llama al método
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.obtenerId(99L);
        });

        // Verifica que el mensaje de la excepción es el esperado
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepositories, times(1)).findById(99L);
    }

    @Test
    void testListarTodas() {
        // 1. Arrange
        // Crea un segundo usuario y una lista
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Usuario 2");
        List<Usuario> listaUsuarios = Arrays.asList(usuario, usuario2);

        // Simula que el repositorio devuelve la lista completa
        when(usuarioRepositories.findAll()).thenReturn(listaUsuarios);

        // 2. Act
        List<Usuario> resultado = usuarioServices.listarTodas();

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size()); // Verifica el tamaño de la lista
        assertEquals("Usuario de Prueba", resultado.get(0).getNombre());
        verify(usuarioRepositories, times(1)).findAll();
    }

    @Test
    void testEliminar_Exitoso() {
        // 1. Arrange
        // Simula que el usuario SÍ existe
        when(usuarioRepositories.existsById(1L)).thenReturn(true);
        // Simula el comportamiento del método deleteById (no hace nada por ser void)
        doNothing().when(usuarioRepositories).deleteById(1L);

        // 2. Act
        // Llama al método (no debería lanzar excepción)
        usuarioServices.eliminar(1L);

        // 3. Assert
        // Verifica que se llamó a existsById y luego a deleteById
        verify(usuarioRepositories, times(1)).existsById(1L);
        verify(usuarioRepositories, times(1)).deleteById(1L);
    }

    @Test
    void testEliminar_NoEncontrado() {
        // 1. Arrange
        // Simula que el usuario NO existe
        when(usuarioRepositories.existsById(99L)).thenReturn(false);

        // 2. Act & 3. Assert
        // Verifica que se lanza la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.eliminar(99L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        // Verifica que existsById se llamó...
        verify(usuarioRepositories, times(1)).existsById(99L);
        // ...pero deleteById NUNCA se llamó
        verify(usuarioRepositories, never()).deleteById(44L);
    }

    @Test
    void testActualizar_Exitoso() {
        // 1. Arrange
        // Datos nuevos para el usuario
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nombre Actualizado");

        // Simula la búsqueda del usuario existente (método obtenerRun)
        when(usuarioRepositories.findById(1L)).thenReturn(Optional.of(usuario));

        // Simula el guardado del usuario ya modificado
        // Usamos thenAnswer para verificar que el objeto guardado tiene los datos
        // nuevos
        when(usuarioRepositories.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario userGuardado = invocation.getArgument(0);
            assertEquals(1L, userGuardado.getId()); // El ID debe ser el mismo
            assertEquals("Nombre Actualizado", userGuardado.getNombre()); // El nombre debe estar actualizado
            return userGuardado;
        });

        // 2. Act
        Usuario resultado = usuarioServices.actualizar(1L, usuarioActualizado);

        // 3. Assert
        assertNotNull(resultado);
        assertEquals("Nombre Actualizado", resultado.getNombre());
        verify(usuarioRepositories, times(1)).findById(1L);
        verify(usuarioRepositories, times(1)).save(usuario); // Verifica que se guardó el objeto 'usuario' (el original
                                                             // modificado)
    }

    @Test
    void testActualizar_NoEncontrado() {
        // 1. Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nombre Actualizado");

        // Simula que el usuario a actualizar no se encuentra (método obtenerRun)
        when(usuarioRepositories.findById(99L)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        // Verifica que se propaga la excepción de "Usuario no encontrado"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioServices.actualizar(99L, usuarioActualizado);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepositories, times(1)).findById(99L);
        // Verifica que NUNCA se intentó guardar nada
        verify(usuarioRepositories, never()).save(any(Usuario.class));
    }
}
