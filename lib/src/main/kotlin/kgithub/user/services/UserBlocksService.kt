package kgithub.user.services

import io.ktor.client.features.*
import io.ktor.client.statement.*
import kgithub.GitHub
import kgithub.GitHubRequester
import kgithub.user.dtos.UserResponse
import kgithub.user.services.UserPaths.BLOCKS_PATH
import kgithub.user.services.UserPaths.ME_PATH

suspend fun GitHub.usersBlockedByMe(): List<UserResponse> =
    GitHubRequester.get(
        path = "$ME_PATH$BLOCKS_PATH",
        accessToken = this.accessToken
    )

suspend fun GitHub.isUserBlockedByMe(userName: String): Boolean =
    try {
        GitHubRequester.get<HttpResponse>(
            path = "$ME_PATH$BLOCKS_PATH/$userName",
            accessToken = this.accessToken
        ).status.value == 204
    } catch (e: Exception) {
        when {
            e is ClientRequestException && e.response.status.value == 404 -> false
            else -> throw e
        }
    }

suspend fun GitHub.blockUser(userName: String): Unit =
    GitHubRequester.put(
        path = "$ME_PATH$BLOCKS_PATH/$userName",
        accessToken = this.accessToken
    )

suspend fun GitHub.unblockUser(userName: String): Unit =
    GitHubRequester.delete(
        path = "$ME_PATH$BLOCKS_PATH/$userName",
        accessToken = this.accessToken
    )
