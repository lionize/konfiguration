package io.chanchan.konfiguration

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.util.*

class KonfigurationBuilderTest : Spek({
    describe("addSource") {
        val konfigurationBuilder = KonfigurationBuilder()

        it("should be able to load a map") {
            val map = mapOf(
                    "ENV" to "dev"
            )

            konfigurationBuilder.apply {
                addSource(map)
            }

            expect(konfigurationBuilder.propertiesList["ENV"]).to.equal("dev")
        }

        it("should be able to load Properties") {
            val properties = Properties()
            properties.setProperty("ENV", "dev")

            konfigurationBuilder.apply {
                addSource(properties)
            }

            expect(konfigurationBuilder.propertiesList["ENV"]).to.equal("dev")
        }
    }
})