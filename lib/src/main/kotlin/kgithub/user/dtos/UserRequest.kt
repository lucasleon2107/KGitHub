package kgithub.user.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("blog")
    val blog: String? = null,
    @SerialName("twitter_username")
    val twitterUsername: String? = null,
    @SerialName("company")
    val company: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("hireable")
    val hireable: Boolean? = null,
    @SerialName("bio")
    val bio: String? = null
)
