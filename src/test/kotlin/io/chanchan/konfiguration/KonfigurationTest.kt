package io.chanchan.konfiguration

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class KonfigurationTest : Spek({
    val properties = hashMapOf(
            "username" to "username",
            "password" to "password"
    )
    val konfiguration = KonfigurationBuilder().apply {
        addSource(properties)
    }.build()

    describe("konf") {
        it("should inject values into instances") {
            val test = TestInstance()
            konfiguration.konf(test)

            expect(test.username).to.equal(properties["username"])
            expect(test.password).to.equal(properties["password"])
        }

        it("should inject values in objects") {
            val constants = object {
                @Property("username") val username: String = ""
                @Property("password") val password: String = ""
            }
            konfiguration.konf(constants)

            expect(constants.username).to.equal(properties["username"])
            expect(constants.password).to.equal(properties["password"])
        }
    }
})

class TestInstance(
        @Property("username") val username: String = "",
        @Property("password") val password: String = ""
)