package cl.duoc.risani.sosdrink.backend.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String run;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;

    private String clave;
    private Long tipoUsuarioId;
    private String tipoUsuarioNombre;

    public boolean isValidFull() {
        return (
            this.isClaveValid()
            && this.isValidInfo()
            && this.isClaveValid()
            && (this.direccion != null && !this.direccion.trim().isEmpty())
            && (this.tipoUsuarioId != null)
        );
    }

    public boolean isValidSignup() {
        return (
            this.isValidBasic()
            && this.isValidInfo()
            && this.isClaveValid()
        );
    }

    public boolean isValidInfo() {
        return (
            this.nombre != null && !this.nombre.trim().isEmpty()
            && this.apellidos != null && !this.apellidos.trim().isEmpty()
        );
    }

    public boolean isValidBasic() {
        return (
            this.isRunValid() == 0
            && this.isCorreoValid()
        );
    }

    public boolean isCorreoValid() {
        if (this.correo == null) return false;
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(this.correo);
        return matcher.matches();
    }

    public boolean isClaveValid() {
        return (this.clave != null && !this.clave.trim().isEmpty());
    }

    public int isRunValid() {
        if (this.run == null) return 1;

        // Validar formato XX.XXX.XXX-X
        Pattern pattern = Pattern.compile("^\\d{7,8}-[\\dKk]$");
        Matcher matcher = pattern.matcher(this.run);
        if (!matcher.matches()) return 2;

        // Validar dígito verificador
        String runBase = this.run.split("-")[0];
        String runDv = this.run.split("-")[1];

        int suma = 0;
        int[] weights = { 2, 3, 4, 5, 6, 7 };
        int wi = 0;

        for (int i = runBase.length() - 1; i >= 0; i--) {
            suma += Integer.parseInt(String.valueOf(runBase.charAt(i))) * weights[wi];
            wi = (wi + 1) % weights.length;
        }

        int d = 11 - (suma % 11);
        String dv = (d == 10) ? "K" : String.valueOf(d % 11);

        if (!runDv.equals(dv)) return 3;

        return 0;
    }

}