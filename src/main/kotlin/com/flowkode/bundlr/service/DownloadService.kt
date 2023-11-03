package com.flowkode.bundlr.service

import jakarta.enterprise.context.ApplicationScoped
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

@ApplicationScoped
class DownloadService {
    fun download(url: URL, out: OutputStream) {
        url.openStream().use { downloadedFile ->
            downloadedFile.copyTo(out)
        }
    }
    fun download(url: URL): InputStream {
        return url.openStream();
    }
}
