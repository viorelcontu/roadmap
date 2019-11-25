package com.endava.practice.roadmap.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_history")
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditHistory {

    @Id
    @Column
    @Basic
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer paymentId;

    @Column
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(
        name = "client_id", referencedColumnName = "user_id",
        insertable = false, updatable = false)
    private User client;

    @OneToOne
    @JoinColumn(
        name = "accountant_id", referencedColumnName = "user_id",
        insertable = false, updatable = false)
    private User accountant;

    @Column
    private BigDecimal amount;
}
