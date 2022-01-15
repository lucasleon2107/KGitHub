package kgithub.user.dtos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hovercard(
    @SerialName("contexts")
    val contexts: List<Context?>? = null
) {
    @Serializable
    data class Context(
        @SerialName("message")
        val message: String? = null,
        @SerialName("octicon")
        val octicon: String? = null
    )
}