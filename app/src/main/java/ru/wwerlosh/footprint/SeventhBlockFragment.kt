package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.util.GlobalData

class SeventhBlockFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seventhblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalEmissionTextView = view.findViewById<TextView>(R.id.totalEmissionTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        totalEmissionTextView.text = GlobalData.total.toString()
        nameTextView.text = GlobalData.name
    }
}