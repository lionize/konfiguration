package io.chanchan.konfiguration

import java.util.*

class KonfigurationBuilder {
    internal val propertiesList = hashMapOf<String, String>()

    fun addSource(map: Map<String, Any>) {
        map.forEach { k, v ->
            if (!propertiesList.containsKey(k))
                propertiesList.put(k, v.toString())
        }
    }

    fun addSource(properties: Properties) {
        properties.forEach { k, v ->
            if (!propertiesList.containsKey(k))
                propertiesList.put(k.toString(), v.toString())
        }
    }

    fun build() = Konfiguration(propertiesList)
}
