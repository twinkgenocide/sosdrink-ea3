package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolioGenerator {
    
    @Id
    private Integer year;
    private Integer currentNumber;

}
