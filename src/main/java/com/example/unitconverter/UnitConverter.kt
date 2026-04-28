package com.example.unitconverter

object UnitConverter {

    fun convert(category: String, from: String, to: String, value: Double): Double {
        return when (category) {
            "Length" -> convertWithFactor(value, from, to, lengthFactors)
            "Weight" -> convertWithFactor(value, from, to, weightFactors)
            "Temperature" -> convertTemperature(value, from, to)
            else -> value
        }
    }

    private fun convertWithFactor(value: Double, from: String, to: String, factors: Map<String, Double>): Double {
        val fromFactor = factors[from] ?: 1.0
        val toFactor = factors[to] ?: 1.0
        val baseValue = value * fromFactor
        return baseValue / toFactor
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5 / 9
            "Kelvin" -> value - 273.15
            else -> value
        }

        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> (celsius * 9 / 5) + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }

    val lengthFactors = mapOf(
        "Meter" to 1.0,
        "Kilometer" to 1000.0,
        "Centimeter" to 0.01,
        "Millimeter" to 0.001,
        "Mile" to 1609.344,
        "Yard" to 0.9144,
        "Foot" to 0.3048,
        "Inch" to 0.0254
    )

    val weightFactors = mapOf(
        "Kilogram" to 1.0,
        "Gram" to 0.001,
        "Milligram" to 0.000001,
        "Pound" to 0.453592,
        "Ounce" to 0.0283495
    )

    val temperatureUnits = listOf("Celsius", "Fahrenheit", "Kelvin")
}
