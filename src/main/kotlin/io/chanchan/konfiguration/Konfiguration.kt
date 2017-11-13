package io.chanchan.konfiguration

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.KCallable

class Konfiguration(private val properties: HashMap<String, String>) {
    fun konf(obj: Any) {
        val propertyMembers = obj::class.members.filter { member ->
            member.annotations.any { it is Property }
        }

        propertyMembers.forEach { prop ->
            val propertyAnnotation = prop.annotations.find { it is Property } as Property
            val value = properties[propertyAnnotation.name]

            if (value != null) {
                injectValueIntoObject(obj, prop, value)
            }
        }
    }

    private fun injectValueIntoObject(obj: Any, prop: KCallable<*>, value: String) {
        val field = obj::class.java.getDeclaredField(prop.name)
        val modifiers = Field::class.java.getDeclaredField("modifiers")
        val fieldModifiers = field.modifiers
        val originalAccessibility = field.isAccessible
        val originalModifiersAccessibility = modifiers.isAccessible
        var modifiersAltered = false

        if (Modifier.isFinal(fieldModifiers)) {
            modifiers.isAccessible = true
            modifiers.setInt(field, fieldModifiers and Modifier.FINAL.inv())
            modifiersAltered = true
        }

        field.isAccessible = true
        field.set(obj, getValue(value, field.type))
        field.isAccessible = originalAccessibility

        if (modifiersAltered) {
            modifiers.setInt(field, fieldModifiers)
            modifiers.isAccessible = originalModifiersAccessibility
        }
    }

    private fun <T> getValue(value: String, type: Class<T>): Any = when (type) {
        String::class.java -> value
        Boolean::class.java -> value.toBoolean()
        Int::class.java -> value.toInt()
        Float::class.java -> value.toFloat()
        else -> throw IllegalArgumentException("Unkown configuration value type: ${type.name}")
    }
}
