package com.endava.practice.roadmap.util;

import com.endava.practice.roadmap.domain.model.entities.User;
import com.endava.practice.roadmap.domain.model.enums.Role;
import com.endava.practice.roadmap.domain.model.internal.UserDto;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

import static com.endava.practice.roadmap.domain.model.enums.Role.*;

@Getter
public enum TestUsers {
    ADMIN_EXISTING(1L, "c4d81355-19dd-4c45-a755-0d19f3ea402c", "vcontu", "Viorel Contu", "viorel@mail.md", ADMIN, 0, true),
    MANAGER_EXISTING(2L, "970f88cb-2900-4530-a4a8-719ae882058c", "asmith", "Agent Smith", "a.smith@mail.com", MANAGER, 0, true),
    ACCOUNTANT_EXISTING(3L, "bd365f12-b18a-4608-946a-d63d456bc361", "lsky", "Luke Skywalker", "l.skywalker@starwars.org", ACCOUNTANT, 0, true),
    CLIENT_EXISTING(4L, "5f0909af-4672-4824-807d-087730c7ec62", "ajoe", "Average Joe", "average@users.net", CLIENT, 0, true),
    CLIENT_NEW(100L, "805bcd75-f9f4-47b9-b83f-51606a3325a9", "wwonka", "Willie Wonka", "w.wonka@gmail.com", CLIENT, 0, true),
    CLIENT_2_NEW(101L, "c100c566-38ea-4e73-96e8-b270f63a2780", "donut", "Mr. Donut", "donut@yammy.com", CLIENT, 0, true),
    CLIENT_NON_EXISTING(999L, null, "nonexisting", null, null, CLIENT, 0, true);

    private final Long id;
    private final UUID token;
    private final String username;
    private final String nickname;
    private final String email;
    private final Role role;
    private final Integer credits;
    private final Boolean active;

    public User buildUser() {
        return User.builder()
            .id(id)
            .token(token)
            .username(username)
            .nickname(nickname)
            .email(email)
            .group(role.getEntity())
            .credits(credits)
            .active(active)
            .build();
    }

    public UserDto buildUserDto() {
        return UserDto.builder()
            .username(username)
            .nickname(nickname)
            .email(email)
            .role(role)
            .credits(credits)
            .active(active)
            .build();
    }

    TestUsers(Long id, String token, String username, String nickname, String email,
              Role role, Integer credits, Boolean active) {
        this.id = id;
        this.token = Optional.ofNullable(token).map(UUID::fromString).orElse(null);
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.credits = credits;
        this.active = active;
    }
}
