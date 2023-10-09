package br.com.fiap.epictask.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo título é obrigatório")
    private String title;

    @Size(min = 10, message = "descrição deve ter pelo menos 10 caracteres")
    private String description;

    @Min(1) @Max(100)
    private Integer score;

    @Min(0) @Max(100)
    private Integer status;
    
}
