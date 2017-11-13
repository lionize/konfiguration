package io.chanchan.konfiguration

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.util.*

class KonfigurationBuilderTest : Spek({
    describe("can load environment variables") {
        val konfigurationBuilder = KonfigurationBuilder()

        it("should be able to load a map") {
            val map = mapOf(
                    "ENV" to "dev"
            )

            val konfiguration = konfigurationBuilder.apply {
                addSource(map)
            }.build()

            expect(konfiguration["ENV"]).to.equal("dev")
        }

        it("should be able to load Properties") {
            val properties = Properties()
            properties.setProperty("ENV", "dev")

            val konfiguration = konfigurationBuilder.apply {
                addSource(properties)
            }.build()

            expect(konfiguration["ENV"]).to.equal("dev")
        }
    }
})