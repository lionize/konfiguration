package io.chanchan.konfiguration

import java.util.*

class KonfigurationBuilder {
    private val propertiesList = mutableMapOf<String, Any>()

    fun addSource(map: Map<String, Any>) {
        map.forEach { k, v ->
            if (!propertiesList.containsKey(k))
                propertiesList.put(k, v)
        }
    }

    fun addSource(properties: Properties) {
        properties.forEach { k, v ->
            if (!propertiesList.containsKey(k))
                propertiesList.put(k.toString(), v)
        }
    }

    fun build() = propertiesList
}
