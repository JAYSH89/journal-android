package nl.jaysh.journal.core.domain.model.food

data class Food(
    val id: String? = null,
    val name: String,
    val carbs: Double,
    val proteins: Double,
    val fats: Double,
    val amount: Double,
    val calories: Double? = null,
    val amountType: AmountType,
)
