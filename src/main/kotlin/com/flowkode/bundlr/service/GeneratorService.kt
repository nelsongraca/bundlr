package com.flowkode.bundlr.service

import com.flowkode.bundlr.model.Part
import com.flowkode.bundlr.model.bom.BOM
import com.flowkode.bundlr.model.form.FormComponent
import com.flowkode.bundlr.model.form.FormComponentRequest
import com.flowkode.bundlr.model.form.FormOption
import com.flowkode.bundlr.model.form.ProjectForm
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URI
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.Path
import kotlin.io.path.name


@ApplicationScoped
class GeneratorService {

    @Inject
    @field: Default
    lateinit var projectService: ProjectService

    @Inject
    @field: Default
    lateinit var downloadService: DownloadService

//    fun generateForm(projectCode: String): Uni<ProjectForm> {
//        return projectService.getProject(projectCode).onItem().transform { a ->
//            val formComponents = mutableListOf<FormComponent>()
//
//            for (component in a.components) {
//                formComponents.add(
//                    FormComponent(
//                        component.id,
//                        component.name,
//                        component.parts
//                            .map { v ->
//                                FormOption(v.id, v.name)
//                            }
//                    )
//                )
//
//            }
//
//            ProjectForm(formComponents)
//        }
//    }

//    fun generateZipBundle(projectCode: String, data: List<FormComponentRequest>): Uni<InputStream> {
//        Log.info("Bundling $projectCode with components $data")
//
//        return projectService.getProject(projectCode).onItem().transform { a ->
//            val outStream = ByteArrayOutputStream()
//
//            val baseUri = URI.create(a.config.baseUrl)
//
//            ZipOutputStream(outStream).use { out ->
//                val parts = data.map { datum ->
//                    val part = a.components
//                        .find { component -> component.id == datum.id }
//                        ?.parts
//                        ?.find { part -> part.id == datum.value }
//
//                    val ret = mutableListOf(part)
//                    if (part != null) {
//                        ret.addAll(getAllPartsRecursively(part))
//                    }
//                    ret
//                }.flatten().filterNotNull().filter { p -> p.file != null }
//
//                Log.debug("Computed parts: ${parts.joinToString(separator = ", ") { p -> p.id }}", )
//
//                for (part in parts) {
//                    if (part.file != null) {
//                        Log.info("Adding $part to zip", )
//
//                        out.putNextEntry(ZipEntry(Path(part.file).fileName.name))
//                        val url = URI(baseUri.scheme, baseUri.userInfo, baseUri.host, baseUri.port, baseUri.path + part.file, baseUri.query, baseUri.fragment).toURL()
//                        Log.info("Downloading $url", )
//                        try {
//                            downloadService.download(url).onItem().invoke { b -> out.write(b) }
//                        } catch (_: FileNotFoundException) {
//                            Log.warn("Part not found $part", )
//                        } catch (e: Exception) {
//                            Log.warn("Could not download part $part", e)
//                        }
//                    } else {
//                        Log.info("Skipping add $part to zip because file is null.", )
//                    }
//                }
//            }
//            ByteArrayInputStream(outStream.toByteArray())
//        }
//    }

    private fun getAllPartsRecursively(part: Part, foundParts: MutableSet<Part> = mutableSetOf()): Set<Part> {
        if (foundParts.contains(part))
            return emptySet()

        foundParts.add(part)
        val parts = part.dependencies.mapTo(HashSet()) { d -> d.part }
        parts.addAll(parts.map { p ->
            getAllPartsRecursively(p, foundParts)
        }.flatten())
        return parts
    }

//    private fun getAllBomPartsRecursively(part: Part, foundParts: MutableSet<Part> = mutableSetOf()): Set<BomPart> {
//        if (foundParts.contains(part))
//            return emptySet()
//
//        foundParts.add(part)
//        val parts = part.dependencies.mapTo(HashSet()) { d -> BomPart(getPartName(d.part), d.amount, d.part.optional, d.part.links) }
//        parts.addAll(part.dependencies.map { p ->
//            getAllBomPartsRecursively(p.part, foundParts)
//        }.flatten())
//        return parts
//    }

//    fun generateBom(projectCode: String, data: List<FormComponentRequest>): Uni<BOM> {
//        Log.info("Generating BOM {} with components {}", projectCode, data.toString())
//
//        return projectService.getProject(projectCode).onItem().transform { a ->
//
//            val parts = data.map { datum ->
//                val component = a.components
//                    .find { component -> component.id == datum.id }
//                val rootPart = component
//                    ?.parts
//                    ?.find { part -> part.id == datum.value }
//
//                val allParts: MutableList<BomPart>
//
//                if (rootPart != null) {
//                    allParts = mutableListOf(BomPart(getPartName(rootPart)))
//                    allParts.addAll(getAllBomPartsRecursively(rootPart))
//                } else {
//                    allParts = mutableListOf()
//                }
//                allParts
//
//            }.flatten().filterNotNull()
//
//            BOM(parts)
//        }
//    }

    private fun getPartName(part: Part): String {
        return if (part.file != null) {
            Path(part.file).fileName.name
        } else {
            part.name
        }
    }
}
