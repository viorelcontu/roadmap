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

    @ApiModelProperty ("User unique id")
    private Long id;

    @ApiModelProperty ("User name")
    @NotNull
    private String name;
}
