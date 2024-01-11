package com.lrogozinski.pennywise.navigation.routes

import com.lrogozinski.pennywise.navigation.NavigationArgs

abstract class RoutesNode(
    private val name: String,
    private val parent: RoutesNode? = null
) {
    protected fun route(path: String): String {
        val route = "$name/$path/{${NavigationArgs.Key}}"
        var currentParent = parent

        return buildString {
            while (currentParent != null) {
                insert(0, "${currentParent!!.name}/")
                currentParent = currentParent!!.parent
            }
            append(route)
        }
    }
}
