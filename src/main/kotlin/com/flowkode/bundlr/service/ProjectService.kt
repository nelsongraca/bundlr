package com.flowkode.bundlr.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.flowkode.bundlr.config.CoreConfig
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
    fun getProject(projectCode: String): Uni<Project> {
        val project = coreConfig.allowedProjects().find { ap -> ap.name() == projectCode }

        if (project == null) {
            throw ProjectNotFoundException("Project $projectCode does not exist")
        }

        if (project.config().startsWith("/"))
            return Uni.createFrom().item(objectMapper.readValue(ProjectService::class.java.getResource(project.config()), Project::class.java))

        return Uni.createFrom().item(objectMapper.readValue(URL(project.config()), Project::class.java))
    }
}
