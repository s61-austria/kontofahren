package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import domain.KontoUser
import org.joda.time.DateTime
import service.UserService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.inject.Named

@Named
@Stateless
class JwtUtils @Inject constructor(val userService: UserService) {
    private val algorithm by lazy { Algorithm.HMAC256("Waarom is java EE zo'n absolute  kutzooi? Ik snap er echt geen flikker van") }
    private val issuer = "kontofahren-widlfly"
    private val verifier = JWT.require(algorithm).withIssuer(issuer).build()

    private val today get() = DateTime.now().toDate()
    private val oneMonthFromNow get() = DateTime.now().plusMonths(1).toDate()

    /**
     * Creates a signed JWT token
     * @param userId user Id to be encoded in JWT
     * @param username username to be encoded in JWT
     * @param groups A list of groups to be encoded in JWT
     * @return A signed and verified JWT with one month validity
     */
    fun createToken(
        userId: String = "",
        username: String = "",
        groups: Array<String> = emptyArray()
    ) = JWT.create()
        .withIssuer(issuer)
        .withClaim("id", userId)
        .withClaim("username", username)
        .withArrayClaim("groups", groups)
        .withIssuedAt(today)
        .withExpiresAt(oneMonthFromNow)
        .sign(algorithm)

    /**
     * Verifies an incoming token
     * @param token A JWT token to be verified
     */
    fun verifyToken(token: String): DecodedJWT? = try {
        verifier.verify(token)
    } catch (ex: Exception) {
        null
    }

    /**
     * Authenticate a user and create a JWT token
     * @param username username to log in with
     * @param password unhashed password to check
     * @return Valid JWT. If user does not exists or password does not match null is returned
     */
    fun login(username: String, password: String): String? {
        val user = userService.getUserByUsername(username) ?: return null
        // If user is not found return null

        if (sha256(password) != user.password) return null
        // If password does not match return null

        val groups = user.groups
            .map { it.groupName }
            .toTypedArray()

        return createToken(user.uuid, user.userName, groups)
    }

    /**
     * Check if there's a logged in user
     * @param token JWT token
     */
    fun isLoggedIn(token: String) = verifyToken(token) != null

    /**
     * Get the currently logged in user
     * @param token JWT token
     * @return Logged in user. Null when user is not present or token is invalid
     */
    fun loggedInUser(token: String): KontoUser? {
        val verifiedToken = verifyToken(token) ?: return null

        val username = verifiedToken.getClaim("username").asString()

        return userService.getUserByUsername(username)
    }

    /**
     * Check if the currently logged in user is part of a group
     * @param token JWT token
     * @param groupname Groupname to check against
     * @return If user in group. If no user present default to false
     */
    fun partOfGroup(token: String, groupname: String) = verifyToken(token)
        ?.getClaim("groups")
        ?.asArray(String::class.java)
        ?.any { it == groupname } ?: false
}
