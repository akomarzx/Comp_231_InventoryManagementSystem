package org.group4.comp231.inventorymanagementservice.domain.static_code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "code_book")
public class CodeBook {
    @Id
    @Column(name = "code_book_id", nullable = false)
    private Long id;

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

    @OneToMany(mappedBy = "codeBook", fetch = FetchType.EAGER)
    private Set<CodeValue> codeValues = new LinkedHashSet<>();

}