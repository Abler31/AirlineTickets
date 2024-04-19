package com.abler31.airlineapp.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.abler31.airlineapp.R
import com.abler31.airlineapp.shorter.ShorterViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    val vm by viewModels<ProfileViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_profile)

        vm.text.observe(viewLifecycleOwner){
            textView.text = it
        }
    }

}