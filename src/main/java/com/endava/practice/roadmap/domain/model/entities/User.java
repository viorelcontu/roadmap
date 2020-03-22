package com.endava.practice.roadmap.domain.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "users")
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = PRIVATE)
public class User {

    @Column (name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @SequenceGenerator(sequenceName = "sq_user_id", allocationSize = 1, initialValue = 100,  name = "USER_SEQUENCE")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "token", unique = true)
    private UUID token;

    @Column(name = "user_name", nullable = false, unique = true)
    @Length(min = 4, max = 32)
    private String username;

    private String nickname;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn (name = "group_id" )
    @NotNull
    private Group group;

    @PositiveOrZero
    @NotNull
    private Integer credits;

    @Column(nullable = false)
    private Boolean active;
}
