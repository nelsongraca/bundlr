package com.flowkode.bundlr.error

import com.flowkode.bundlr.service.GeneratorService
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.slf4j.LoggerFactory

@Provider
class ConstraintViolationsExceptionProvider : ExceptionMapper<BundlrException> {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(ConstraintViolationsExceptionProvider::class.java)
    }

    override fun toResponse(e: BundlrException): Response {
        if (e is ProjectNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.message).build()
        } else {
            LOGGER.error(e.message, e)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}
