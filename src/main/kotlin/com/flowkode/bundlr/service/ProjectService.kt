package com.flowkode.bundlr.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.flowkode.bundlr.config.CoreConfig
import com.flowkode.bundlr.config.YamlMapper
import com.flowkode.bundlr.error.ProjectNotFoundException
import com.flowkode.bundlr.model.Project
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
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

    fun getProject(projectCode: String): Uni<Project> {
        val project = coreConfig.allowedProjects().find { ap -> ap.name() == projectCode }

        if (project == null) {
            throw ProjectNotFoundException("Project $projectCode does not exist")
        }

        val resource = if (project.config().startsWith("/")) {
            ProjectService::class.java.getResource(project.config())!!
        } else {
            URL(project.config())
        }

        return if (project.config().endsWith(".json")) {
            Uni.createFrom().item(objectMapper.readValue(resource, Project::class.java))
        } else if (project.config().endsWith(".yml") || project.config().endsWith(".yaml")) {
            Uni.createFrom().item(yamlMapper.readValue(resource, Project::class.java))
        } else {
            throw ProjectNotFoundException()
        }
    }
}
