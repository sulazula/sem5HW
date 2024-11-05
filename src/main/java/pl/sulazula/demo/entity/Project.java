package pl.sulazula.demo.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Project`s ID", example = "7")
    private Long id;

    @Schema(description = "Name of your project", example = "Project ALPHA")
    @Column(name = "name")
    private String name;

    @Schema(description = "Description of this project", example = "This is the Project ALPHA ...")
    @Column(name = "description")
    private String description;

    @Schema(description = "Date when project was created", example = "2022-02-23")
    @Column(name = "createdDate")
    private LocalDate createdDate;
}
