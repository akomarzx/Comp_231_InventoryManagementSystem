package org.group4.comp231.inventorymanagementservice.domain.static_code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "code_value")
public class CodeValue {
    @Id
    @Column(name = "code_value_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "code_book_id", nullable = false)
    @JsonIgnore
    private Long codeBook;

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @JsonIgnore
    private Instant createdAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "created_by", nullable = false)
    @JsonIgnore
    private String createdBy;

    @Column(name = "updated_at")
    @JsonIgnore
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;

}