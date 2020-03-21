package com.endava.practice.roadmap.domain.model.internal;

import com.endava.practice.roadmap.domain.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = PRIVATE)
@JsonPropertyOrder ({"username", "nickname", "email", "role", "credits", "active"})
public class UserDto {

    @EqualsAndHashCode.Include
    @Pattern(regexp = "^(?=.{4,32}$)(?![_])(?!.*[_]{2})[a-z0-9_]+(?<![_])$", message = "Please select a proper username")
    @NotNull
    private String username;

    private String nickname;

    @Email
    private String email;

    @NotNull
    private Role role;

    @PositiveOrZero
    private Integer credits;

    private Boolean active;
}
