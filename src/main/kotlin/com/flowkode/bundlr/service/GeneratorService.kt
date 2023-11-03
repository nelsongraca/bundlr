package com.flowkode.bundlr.service

import com.flowkode.bundlr.model.form.FormComponent
import com.flowkode.bundlr.model.form.FormComponentRequest
import com.flowkode.bundlr.model.form.FormOption
import com.flowkode.bundlr.model.form.ProjectForm
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.net.URI
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.Path
import kotlin.io.path.name


@ApplicationScoped
class GeneratorService {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(GeneratorService::class.java)
    }

    @Inject
    @field: Default
    lateinit var projectService: ProjectService

    fun generateForm(projectCode: String): Uni<ProjectForm> {
        return projectService.getProject(projectCode).onItem().transform { a ->
            val formComponents = mutableListOf<FormComponent>()

            for (component in a.components) {
                formComponents.add(
                    FormComponent(
                        component.id,
                        component.name,
                        component.parts
                            .map { v ->
                                FormOption(v.id, v.name)
                            }
                    )
                )

            }

            ProjectForm(formComponents)
        }
    }

    fun generateZipBundle(projectCode: String, data: List<FormComponentRequest>): Uni<InputStream> {
        return projectService.getProject(projectCode).onItem().transform { a ->
            val outStream = ByteArrayOutputStream()

            val baseUri = URI.create(a.config.baseUrl)


            ZipOutputStream(outStream).use { out ->
                val parts = data.map { datum ->
                    a.components
                        .find { component -> component.id == datum.id }
                        ?.parts
                        ?.find { part -> part.id == datum.value }
                }.filterNotNull()
                for (part in parts) {
                    LOGGER.debug("Adding {} to zip", part)
                    out.putNextEntry(ZipEntry(Path(part.file!!).fileName.name))
                    val url = URI(baseUri.scheme, baseUri.userInfo, baseUri.host, baseUri.port, baseUri.path + part.file, baseUri.query, baseUri.fragment).toURL()
                    LOGGER.debug("Downloading {}", url)
                    url.openStream().use { downloadedFile ->
                        downloadedFile.copyTo(out)
                    }
                }

            }
            ByteArrayInputStream(outStream.toByteArray())
        }
    }
}
