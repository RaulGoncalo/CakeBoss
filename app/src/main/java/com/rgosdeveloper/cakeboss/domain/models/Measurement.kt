package com.rgosdeveloper.cakeboss.domain.models

enum class Measurement (val unit: String, val description: String){
    GRAM("g", "gramas"),
    KILOGRAM("kg", "kilogramas"),
    LITER("L", "litros"),
    MILLILITER("mL", "mililitros"),
    UNIT("unit", "unidade")
}