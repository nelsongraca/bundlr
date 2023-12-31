package com.flowkode.bundlr.api


import com.flowkode.bundlr.model.bom.BOM
import com.flowkode.bundlr.model.form.FormComponentRequest
import com.flowkode.bundlr.model.form.ProjectForm
import com.flowkode.bundlr.service.GeneratorService
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.MultivaluedMap
import jakarta.ws.rs.core.Response
import org.slf4j.LoggerFactory


@Path("/generate")
class GeneratorResource {

    @Inject
    @field: Default
    lateinit var generatorService: GeneratorService

//    @GET
//    @Path("/forminfo/{projectCode}")
//    @Produces(MediaType.APPLICATION_JSON)
//    fun formInfo(@PathParam("projectCode") projectCode: String): Uni<ProjectForm> {
//        return generatorService.generateForm(projectCode)
//    }
//
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Path("/bundle/{projectCode}")
//    fun bundle(@PathParam("projectCode") projectCode: String, formData: MultivaluedMap<String, String>): Uni<Response> {
//        val data = formData.entries
//            .map { e -> FormComponentRequest(id = e.key, value = e.value[0]) }
//
//        return generatorService.generateZipBundle(projectCode, data).onItem()
//            .transform { inStream ->
//                Response
//                    .ok(inStream)
//                    .header("Content-Type", "application/zip")
//                    .build()
//            }
//    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/bom/{projectCode}")
    fun bom(@PathParam("projectCode") projectCode: String, formData: MultivaluedMap<String, String>): Uni<BOM> {
        val data = formData.entries
            .map { e -> FormComponentRequest(id = e.key, value = e.value[0]) }

//        return generatorService.generateBom(projectCode, data)
        return Uni.createFrom().nullItem()
    }
}