package com.flowkode.bundlr.error

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ConstraintViolationsExceptionProvider : ExceptionMapper<ProjectNotFoundException> {
    override fun toResponse(e: ProjectNotFoundException): Response {
        return Response.status(Response.Status.NOT_FOUND).entity(e.message).build()
    }
}
