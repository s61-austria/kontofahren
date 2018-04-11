package utils

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import domain.KontoGroup
import domain.KontoUser
import org.junit.Before
import org.junit.Test
import service.UserService
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JwtUtilsTest {
    val kontoGroup = KontoGroup("admins")
    val kontoUser = KontoUser("john", sha256("john")).apply { groups.add(kontoGroup) }
    lateinit var jwtUtils: JwtUtils

    @Before
    fun setup() {
        val userServiceMock = mock<UserService> {
            on { getUserByUsername("john") } doReturn kontoUser
        }

        jwtUtils = JwtUtils(userServiceMock)
    }

    @Test
    fun createToken() {
        val token = jwtUtils.createToken()

        print(token)
        assertNotNull(token)
    }

    @Test
    fun verifyToken() {
        val token = jwtUtils.createToken()

        assertTrue { jwtUtils.verifyToken(token)!!.getClaim("username").asString() != null }
    }

    @Test
    fun verifyTokenFail() {
        val token = "haha nee"

        assertNull(jwtUtils.verifyToken(token))
    }

    @Test
    fun login() {
        val token = jwtUtils.login("john", "john")

        assertNotNull(token)

        val verifiedToken = jwtUtils.verifyToken(token!!)

        assertNotEquals(verifiedToken!!.getClaim("groups").asArray(String::class.java), emptyArray())
    }

    @Test
    fun loginFail() {
        val token = jwtUtils.login("steve", "steve")

        assertNull(token)
    }

    @Test
    fun isLoggedIn() {
        val token = jwtUtils.login("john", "john")!!

        assertTrue { jwtUtils.isLoggedIn(token) }
    }

    @Test
    fun isLoggedInFail() {
        assertFalse { jwtUtils.isLoggedIn("haha nee") }
    }

    @Test
    fun loggedInUser() {
        val token = jwtUtils.login("john", "john")!!

        val user = jwtUtils.loggedInUser(token)

        assertNotNull(user)
        assertEquals(user!!.uuid, kontoUser.uuid)
    }

    @Test
    fun partOfGroup() {
        val token = jwtUtils.login("john", "john")!!

        assertTrue { jwtUtils.partOfGroup(token, "admins") }
        assertFalse { jwtUtils.partOfGroup(token, "jostis") }
    }
}
