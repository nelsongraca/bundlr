package com.flowkode.bundlr.api

import com.flowkode.bundlr.model.form.FormComponentRequest
import com.flowkode.bundlr.model.form.ProjectForm
import com.flowkode.bundlr.service.GeneratorService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.MultivaluedMap
import jakarta.ws.rs.core.Response
import java.util.stream.Collectors


@Path("/generator")
class GeneratorResource {

    @Inject
    @field: Default
    lateinit var generatorService: GeneratorService

    @GET
    @Path("/forminfo/{projectCode}")
    @Produces(MediaType.APPLICATION_JSON)
    fun test(@PathParam("projectCode") projectCode: String): Uni<ProjectForm> {
        return generatorService.generateForm(projectCode)
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/bundle/{projectCode}")
    fun generateFile(@PathParam("projectCode") projectCode: String, formData: MultivaluedMap<String, String>): Uni<Response> {
        val data = formData.entries
            .map { e -> FormComponentRequest(id = e.key, value = e.value[0]) }

        return generatorService.generateZipBundle(projectCode, data).onItem()
            .transform { inStream ->
                Response
                    .ok(inStream)
                    .header("Content-Type", "application/zip")
                    .build()
            }
    }
}