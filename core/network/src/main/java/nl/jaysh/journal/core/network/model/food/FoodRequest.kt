package nl.jaysh.journal.core.network.model.food

import kotlinx.serialization.Serializable
import nl.jaysh.journal.core.domain.model.food.AmountType
import nl.jaysh.journal.core.domain.model.food.Food

@Serializable
data class FoodRequest(
    val id: String?,
    val name: String,
    val carbs: Double,
    val proteins: Double,
    val fats: Double,
    val amount: Double,
    val amountType: AmountType,
) {
    companion object {
        fun fromFood(food: Food) = FoodRequest(
            id = food.id,
            name = food.name,
            carbs = food.carbs,
            proteins = food.proteins,
            fats = food.fats,
            amount = food.amount,
            amountType = food.amountType,
        )
    }
}
