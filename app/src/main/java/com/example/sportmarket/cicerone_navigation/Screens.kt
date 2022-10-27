package com.example.sportmarket.cicerone_navigation

import com.example.sportmarket.fragments.ProductFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun productFragment() = FragmentScreen { ProductFragment() }
}