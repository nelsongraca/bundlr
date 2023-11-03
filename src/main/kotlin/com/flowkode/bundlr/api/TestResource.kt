package com.flowkode.bundlr.api

import com.flowkode.bundlr.model.*
import com.flowkode.bundlr.service.GeneratorService
import com.flowkode.bundlr.service.ProjectService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/test")
class TestResource {


    @Inject
    @field: Default
    lateinit var projectService: ProjectService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun test(): Uni<Project> {
        return projectService.getProject("asd")
    }
}