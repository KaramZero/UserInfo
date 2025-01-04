package com.madarsoft.core.domain.model

enum class Gender( val value: String) {
    MALE("male"),
    FEMALE("female");

    companion object {
        infix fun from(value: String?): Gender? =
            entries.firstOrNull { it.value.equals(value,true) }
    }
}