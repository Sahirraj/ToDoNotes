package com.example.myapplication.clicklisteners.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.myapplication.R

class OnBoardingTwoFragment : Fragment() {
    lateinit var textViewBack: TextView
    lateinit var textViewDone:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    private fun bindView(view: View) {
        textViewDone = view.findViewById(R.id.textViewDone)
        textViewBack = view.findViewById(R.id.textViewBack)

    }


}
