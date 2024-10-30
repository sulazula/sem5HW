package pl.sulazula.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UsersProject extends EntityWithRelation {

    @Column(nullable = false, name = "projectID")
    private Long projectId;
    @Column(nullable = false, name = "userID")
    private Long userId;
}
