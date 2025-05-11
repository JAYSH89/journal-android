package nl.jaysh.journal.core.network.model.food

import kotlinx.serialization.Serializable
import nl.jaysh.journal.core.domain.model.food.AmountType
import nl.jaysh.journal.core.domain.model.food.Food

@Serializable
data class FoodResponse(
    val id: String,
    val name: String,
    val carbs: Double,
    val proteins: Double,
    val fats: Double,
    val amount: Double,
    val calories: Double,
    val amountType: AmountType,
)

fun FoodResponse.toFood() = Food(
    id = id,
    name = name,
    carbs = carbs,
    proteins = proteins,
    fats = fats,
    amount = amount,
    calories = calories,
    amountType = amountType,
)
