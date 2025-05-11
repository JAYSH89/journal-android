package nl.jaysh.journal.core.network.service

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.DataError.Network.UNKNOWN
import nl.jaysh.journal.core.network.model.WeightMeasurementResponse
import nl.jaysh.journal.core.network.model.food.FoodRequest
import nl.jaysh.journal.core.network.model.food.FoodResponse
import javax.inject.Inject

class JournalService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun getWeight(): Either<DataError, List<WeightMeasurementResponse>> {
        val path = "v1/weight-measurement"
        val response = httpClient.get(path)

        return if (response.status.isSuccess()) {
            Right(response.body<List<WeightMeasurementResponse>>())
        } else {
            Left(UNKNOWN)
        }
    }

    suspend fun saveWeightMeasurement(weight: Double): Either<DataError, WeightMeasurementResponse> {
        val path = "v1/weight-measurement"
        val response = httpClient.post(path) {
            setBody(mapOf("weight" to weight))
        }

        return if (response.status.isSuccess()) {
            Right(response.body<WeightMeasurementResponse>())
        } else {
            Left(UNKNOWN)
        }
    }

    suspend fun deleteWeight(id: String): Either<DataError, Unit> {
        val path = "v1/weight-measurement/$id"
        val response = httpClient.delete(path)

        return if (response.status.isSuccess()) {
            Right(Unit)
        } else {
            Left(UNKNOWN)
        }
    }

    suspend fun getFood(): Either<DataError, List<FoodResponse>> {
        val path = "v1/food"
        val response = httpClient.get(path)

        return if (response.status.isSuccess()) {
            Right(response.body<List<FoodResponse>>())
        } else {
            Left(UNKNOWN)
        }
    }

    suspend fun saveFood(food: FoodRequest): Either<DataError, FoodResponse> {
        val path = "v1/food"
        val response = httpClient.post(path) {
            setBody(food)
        }

        return if (response.status.isSuccess()) {
            Right(response.body<FoodResponse>())
        } else {
            Left(UNKNOWN)
        }
    }

    suspend fun deleteFood(id: String): Either<DataError, Unit> {
        val path = "v1/food/$id"
        val response = httpClient.delete(path)

        return if (response.status.isSuccess()) {
            Right(Unit)
        } else {
            Left(UNKNOWN)
        }
    }
}
