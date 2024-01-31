package org.group4.comp231.inventorymanagementservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "view_get_static_values")
public class ViewGetStaticValue {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_value_id", nullable = false)
    private Long codeValueId;

    @NotNull
    @Column(name = "code_book_id", nullable = false)
    private Long codeBookId;

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

}