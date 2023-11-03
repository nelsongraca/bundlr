package com.flowkode.bundlr.api

import com.flowkode.bundlr.model.Project
import com.flowkode.bundlr.service.ProjectService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/config")
class ConfigResource {


    @Inject
    @field: Default
    lateinit var projectService: ProjectService

    @GET
    @Path("/{projectCode}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getConfigForProject(@PathParam("projectCode") projectCode: String): Uni<Project> {
        return projectService.getProject(projectCode)
    }
}