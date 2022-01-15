package kgithub

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json as JsonBuilder

class GitHubRequester {
    companion object {
        @PublishedApi
        internal const val BASE_URL = "https://api.github.com"

        @PublishedApi
        internal val httpClient: HttpClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(JsonBuilder {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }

        @PublishedApi
        internal suspend inline fun <reified T> request(
            path: String,
            httpMethod: HttpMethod,
            accessToken: String? = null,
            requestBody: Any? = null,
            queryParameters: Map<String, Any?>? = null
        ): T =
            httpClient.request("$BASE_URL$path") {
                method = httpMethod
                contentType(ContentType.Application.Json)
                accept(ContentType("application", "vnd.github.v3+json"))
                accessToken?.let {
                    header(HttpHeaders.Authorization, "token $it")
                }
                requestBody?.let {
                    body = it
                }
                queryParameters?.map { queryParam ->
                    queryParam.value?.let {
                        parameter(queryParam.key, queryParam.value)
                    }
                }
            }

        suspend inline fun <reified T> get(
            path: String,
            accessToken: String? = null,
            queryParameters: Map<String, Any?>? = null
        ): T =
            request(
                path = path,
                httpMethod = HttpMethod.Get,
                accessToken = accessToken,
                queryParameters = queryParameters
            )

        suspend inline fun <reified T> post(path: String, accessToken: String? = null, body: Any): T =
            request(path = path, httpMethod = HttpMethod.Post, accessToken = accessToken, requestBody = body)

        suspend inline fun <reified T> patch(path: String, accessToken: String? = null, body: Any): T =
            request(path = path, httpMethod = HttpMethod.Patch, accessToken = accessToken, requestBody = body)

        suspend inline fun <reified T> put(path: String, accessToken: String? = null, body: Any? = null): T =
            request(path = path, httpMethod = HttpMethod.Put, accessToken = accessToken, requestBody = body)

        suspend inline fun <reified T> delete(path: String, accessToken: String? = null): T =
            request(path = path, httpMethod = HttpMethod.Delete, accessToken = accessToken)
    }
}