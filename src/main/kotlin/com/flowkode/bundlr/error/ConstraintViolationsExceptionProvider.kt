package com.flowkode.bundlr.error

import io.quarkus.logging.Log
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ConstraintViolationsExceptionProvider : ExceptionMapper<BundlrException> {

    override fun toResponse(e: BundlrException): Response {
        if (e is ProjectNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.message).build()
        } else {
            Log.error(e.message, e)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}
