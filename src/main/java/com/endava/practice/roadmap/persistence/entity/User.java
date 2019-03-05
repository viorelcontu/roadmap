package com.endava.practice.roadmap.persistence.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements IdEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty (value = "User unique id")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty (value = "User name")
    String name;
}
