package com.abler31.airlineapp.hotels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.abler31.airlineapp.R
import com.abler31.airlineapp.shorter.ShorterViewModel

class HotelFragment : Fragment(R.layout.fragment_hotels) {

    val vm by viewModels<HotelViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_dashboard)

        vm.text.observe(viewLifecycleOwner){
            textView.text = it
        }
    }

}