package org.http4k.contract.openapi.v2

import org.http4k.contract.openapi.Render
import org.http4k.contract.openapi.SecurityRenderer
import org.http4k.contract.security.ApiKeySecurity
import org.http4k.contract.security.BasicAuthSecurity
import org.http4k.contract.security.NoSecurity
import org.http4k.contract.security.Security

object OpenApi2SecurityRenderer : SecurityRenderer {
    override fun <NODE> full(security: Security): Render<NODE>? =
        when (security) {
            is BasicAuthSecurity -> {
                {
                    obj(security.name to obj("type" to string("basic")))
                }
            }
            is ApiKeySecurity<*> -> {
                {
                    obj(security.name to obj(
                        "type" to string("apiKey"),
                        "in" to string(security.param.meta.location),
                        "name" to string(security.param.meta.name)
                    ))
                }
            }
            is NoSecurity -> {
                {
                    nullNode()
                }
            }
            else -> null
        }

    override fun <NODE> ref(security: Security): Render<NODE>? =
        when (security) {
            is ApiKeySecurity<*> -> {
                { obj(security.name to array(emptyList())) }
            }
            is BasicAuthSecurity -> {
                { obj(security.name to array(emptyList())) }
            }
            else -> null
        }
}