package com.ajulay.awsdemo

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.util.UUID


@Entity
class Employee(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column
    val name: String,

    @Column
    val age: LocalDate,

)