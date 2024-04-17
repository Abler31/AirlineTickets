package com.abler31.airlineapp.ui.tickets

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abler31.airlineapp.R
import com.abler31.airlineapp.ui.shorter.ShorterViewModel

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    val vm by viewModels<TicketsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.text_home)

        vm.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

}