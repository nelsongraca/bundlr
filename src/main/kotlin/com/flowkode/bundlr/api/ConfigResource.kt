package com.flowkode.bundlr.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.flowkode.bundlr.config.YamlMapper
import com.flowkode.bundlr.model.Project
import com.flowkode.bundlr.service.ProjectService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/config")
class ConfigResource {


    @Inject
    @field: Default
    lateinit var projectService: ProjectService

    @Inject
    @YamlMapper
    lateinit var yamlMapper: ObjectMapper

    @GET
    @Path("/{projectCode}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getConfigForProject(@PathParam("projectCode") projectCode: String): Uni<Project> {
        return projectService.getProject(projectCode)
    }

    @GET
    @Path("/{projectCode}/yaml")
    @Consumes("application/x-yaml")
    @Produces("application/x-yaml")
    fun getYamlConfigForProject(@PathParam("projectCode") projectCode: String): Uni<Response> {
        return getConfigForProject(projectCode).onItem().transform { p ->
            Response.ok(yamlMapper.writeValueAsString(p)).build()
        }
    }
}