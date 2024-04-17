package com.abler31.airlineapp.ui.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.abler31.airlineapp.R
import com.abler31.airlineapp.ui.shorter.ShorterViewModel

class SubscriptionFragment : Fragment(R.layout.fragment_subscription) {

    val vm by viewModels<SubscriptionViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_subscription)

        vm.text.observe(viewLifecycleOwner){
            textView.text = it
        }
    }

}