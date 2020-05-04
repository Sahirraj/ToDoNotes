package com.example.myapplication.clicklisteners.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentmanager: FragmentManager): FragmentStatePagerAdapter(fragmentmanager) {
    override fun getItem(position: Int): Fragment? {
        // two fragments indexes - 0, 1
        when (position) {
            0 -> return OnBoardingOneFragment()
            1 -> return OnBoardingTwoFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 2

    }
}