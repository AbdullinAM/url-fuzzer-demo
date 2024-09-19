package org.plan.research.org.plan.research

import java.net.URI
import java.net.URISyntaxException


object URLValidator {
    fun validate(url: String): Boolean {
        val supportedSchemes: List<String> = mutableListOf("http", "https")
        try {
            val uri: URI = URI(url)
            val scheme: String = uri.scheme
            val host: String = uri.host

            require(supportedSchemes.contains(scheme)) { "Scheme must be one of $supportedSchemes" }
            require(host.isNotEmpty()) { "Host must be non-empty" }

            // Do something with the URL
            return true
        } catch (e: URISyntaxException) {
            throw IllegalArgumentException("Invalid URL format", e)
        }
    }
}
