package kgithub.user.services

import kgithub.GitHub
import kgithub.GitHubRequester
import kgithub.user.services.UserPaths.HOVERCARD_PATH
import kgithub.user.services.UserPaths.ME_PATH
import kgithub.user.services.UserPaths.USERS_PATH
import kgithub.user.dtos.Hovercard
import kgithub.user.dtos.UserResponse

suspend fun GitHub.me(): UserResponse = GitHubRequester.get(path = ME_PATH, accessToken = this.accessToken)

suspend fun GitHub.updateMe(body: Any): UserResponse =
    GitHubRequester.patch(path = ME_PATH, accessToken = this.accessToken, body = body)

suspend fun GitHub.listUsers(since: Int? = null, perPage: Int? = 30): List<UserResponse> =
    GitHubRequester.get(
        path = USERS_PATH,
        accessToken = this.accessToken,
        queryParameters = mapOf(
            "since" to since,
            "per_page" to perPage
        )
    )

suspend fun GitHub.findUser(userName: String): UserResponse =
    GitHubRequester.get(path = "$USERS_PATH/$userName", accessToken = this.accessToken)

suspend fun GitHub.hoverCard(userName: String): Hovercard =
    GitHubRequester.get(
        path = "$USERS_PATH/$userName$HOVERCARD_PATH",
        accessToken = this.accessToken
    )

suspend fun GitHub.hoverCard(userName: String, subjectType: String): Hovercard =
    GitHubRequester.get(
        path = "$USERS_PATH/$userName$HOVERCARD_PATH",
        accessToken = this.accessToken,
        queryParameters = mapOf(
            "subject_type" to subjectType
        )
    )

suspend fun GitHub.hoverCard(userName: String, subjectId: String, subjectType: String): Hovercard =
    GitHubRequester.get(
        path = "$USERS_PATH/$userName$HOVERCARD_PATH",
        accessToken = this.accessToken,
        queryParameters = mapOf(
            "subject_id" to subjectId,
            "subject_type" to subjectType
        )
    )
