package com.example.sportmarket.cicerone_navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

class MyRouter {

    private val cicerone = Cicerone.create()
    fun router() : Router {
        return cicerone.router
    }
    fun navigatorHolder() : NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}