package com.flowkode.bundlr.service

import io.quarkus.cache.Cache
import io.quarkus.cache.CacheName
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.io.ByteArrayOutputStream
import java.net.URL


@ApplicationScoped
class DownloadService {


    @Inject
    @CacheName("download-cache")
    lateinit var cache: Cache

    fun download(url: URL): Uni<ByteArray> {
        return cache.get(url.toString()) { _ ->
            url.openStream().use { file ->
                val out = ByteArrayOutputStream()
                file.copyTo(out)
                out.toByteArray()
            }
        }
    }
}