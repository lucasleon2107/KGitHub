package kgithub

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json as JsonBuilder

class RestClient {
    companion object {
        internal fun create() = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(JsonBuilder {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }
}