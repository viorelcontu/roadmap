package com.endava.practice.roadmap.domain.model.entities;

import com.endava.practice.roadmap.domain.model.entities.converters.PermissionAttributeConverter;
import com.endava.practice.roadmap.domain.model.enums.Role;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table (name = "groups")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class Group implements ResourceWithId<Integer> {

    @Column(name = "group_id")
    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    @Column (name = "group_name")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "group")
    private Collection<User> users;

    @ElementCollection
    @CollectionTable(name = "groups_permissions", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "permission_id")
    @Convert (converter = PermissionAttributeConverter.class)
    private Set<Permission> permissions = new HashSet<>();
}
