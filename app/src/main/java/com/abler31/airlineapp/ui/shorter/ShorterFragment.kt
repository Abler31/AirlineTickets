package com.abler31.airlineapp.ui.shorter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abler31.airlineapp.R

class ShorterFragment : Fragment(R.layout.fragment_shorter) {

    val vm by viewModels<ShorterViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_notifications)

        vm.text.observe(viewLifecycleOwner){
            textView.text = it
        }
    }
}