package pl.sulazula.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representation of a user's project association")
public class UsersProject extends EntityWithRelation {

    @Column(nullable = false, name = "projectid")
    @Schema(description = "ID of the project", example = "1", required = true)
    private Long projectId;

    @Column(nullable = false, name = "userid")
    @Schema(description = "ID of the user", example = "5", required = true)
    private Long userId;
}
