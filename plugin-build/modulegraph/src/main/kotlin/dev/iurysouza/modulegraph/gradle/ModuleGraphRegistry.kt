package dev.iurysouza.modulegraph.gradle

import dev.iurysouza.modulegraph.gradle.graphparser.model.ProjectInfo
import dev.iurysouza.modulegraph.gradle.graphparser.model.ProjectPath
import java.util.concurrent.ConcurrentHashMap
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

/**
 * Build-scoped service that aggregates each project's [ProjectInfo].
 *
 * Projects register their snapshot during configuration; [CreateModuleGraphTask] reads the
 * accumulated map at execution time.
 */
internal abstract class ModuleGraphRegistry : BuildService<BuildServiceParameters.None> {
    private val infos = ConcurrentHashMap<ProjectPath, ProjectInfo>()

    fun register(info: ProjectInfo) {
        infos[info.path] = info
    }

    fun snapshot(): Map<ProjectPath, ProjectInfo> = infos.toMap()

    companion object {
        const val NAME: String = "moduleGraphRegistry"
    }
}
