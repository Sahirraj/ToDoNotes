package com.example.myapplication.clicklisteners.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.myapplication.R

class OnBoardingOneFragment : Fragment() {
    lateinit var textViewNext : TextView
    lateinit var onNextClick : OnNextClick

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    private fun bindView(view: View) {
        textViewNext = view.findViewById(R.id.textViewNext)
        clickListener()
    }

    private fun clickListener() {
        textViewNext.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onNextClick.onClick()
            }

        })
    }

    interface OnNextClick {
        fun onClick(){

        }
    }

}
