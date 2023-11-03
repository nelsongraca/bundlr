package com.flowkode.bundlr.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.flowkode.bundlr.config.CoreConfig
import com.flowkode.bundlr.config.YamlMapper
import com.flowkode.bundlr.error.ProjectNotFoundException
import com.flowkode.bundlr.model.Project
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.net.URL

@ApplicationScoped
class ProjectService {

    @Inject
    lateinit var coreConfig: CoreConfig

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Inject
    @YamlMapper
    lateinit var yamlMapper: ObjectMapper

    @Inject
    @field: Default
    lateinit var downloadService: DownloadService

    fun getProject(projectCode: String): Uni<Project> {
        val project = coreConfig.allowedProjects().find { ap -> ap.name() == projectCode }

        if (project == null) {
            throw ProjectNotFoundException("Project $projectCode does not exist")
        }

        val url = if (project.config().startsWith("/")) {
            ProjectService::class.java.getResource(project.config())!!
        } else {
            URL(project.config())
        }

        val mapper = if (project.config().endsWith(".json")) {
            objectMapper
        } else if (project.config().endsWith(".yml") || project.config().endsWith(".yaml")) {
            yamlMapper
        } else {
            throw ProjectNotFoundException()
        }
        return Uni.createFrom().item(mapper.readValue(downloadService.download(url), Project::class.java))
    }
}
