package com.rgosdeveloper.cakeboss.navigation

object Routes {
    const val MAIN_PAGER = "main_pager"
    private const val INGREDIENT_REGISTER_BASE = "ingredient_register"

    object Arguments {
        const val INGREDIENT_ID = "ingredientId"
    }



    val INGREDIENT_REGISTER = "$INGREDIENT_REGISTER_BASE?${Arguments.INGREDIENT_ID}={${Arguments.INGREDIENT_ID}}"

    fun ingredientRegisterRoute(ingredientId: Int): String {
        return "$INGREDIENT_REGISTER_BASE?${Arguments.INGREDIENT_ID}=$ingredientId"
    }
}