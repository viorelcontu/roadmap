package com.endava.practice.roadmap.domain.model.dto;

import com.endava.practice.roadmap.domain.model.entity.ResourceWithId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements ResourceWithId<Long> {

    //TODO Tokens for users

    private Long id;

    @NotNull
    private String name;
}
