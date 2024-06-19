package com.github.ramonvermeulen.dbtToolkit.ui.cef

import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefCallback
import org.cef.handler.CefRequestHandlerAdapter
import org.cef.handler.CefResourceHandler
import org.cef.handler.CefResourceHandlerAdapter
import org.cef.handler.CefResourceRequestHandler
import org.cef.handler.CefResourceRequestHandlerAdapter
import org.cef.misc.BoolRef
import org.cef.network.CefRequest
import java.net.URI

private typealias CefResourceProvider = () -> CefResourceHandler?

class CefLocalRequestHandler : CefRequestHandlerAdapter() {
    private val myResources: MutableMap<String, CefResourceProvider> = HashMap()

    private val rejectingResourceHandler: CefResourceHandler =
        object : CefResourceHandlerAdapter() {
            override fun processRequest(
                request: CefRequest,
                callback: CefCallback,
            ): Boolean {
                callback.cancel()
                return false
            }
        }

    private val resourceRequestHandler =
        object : CefResourceRequestHandlerAdapter() {
            override fun getResourceHandler(
                browser: CefBrowser?,
                frame: CefFrame?,
                request: CefRequest,
            ): CefResourceHandler {
                val url = URI.create(request.url).toURL()
                return try {
                    val fileName = url.path.split("/").last()
                    myResources[fileName]?.let { it() } ?: rejectingResourceHandler
                } catch (e: RuntimeException) {
                    rejectingResourceHandler
                }
            }
        }

    fun addResource(
        resourcePath: String,
        resourceProvider: CefResourceProvider,
    ) {
        myResources[resourcePath] = resourceProvider
    }

    override fun getResourceRequestHandler(
        browser: CefBrowser?,
        frame: CefFrame?,
        request: CefRequest?,
        isNavigation: Boolean,
        isDownload: Boolean,
        requestInitiator: String?,
        disableDefaultHandling: BoolRef?,
    ): CefResourceRequestHandler {
        return resourceRequestHandler
    }
}
