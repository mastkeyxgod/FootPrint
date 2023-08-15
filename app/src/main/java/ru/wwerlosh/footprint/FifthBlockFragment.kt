package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class FifthBlockFragment : Fragment() {

    final val AIRCRAFT_COEFFICIENT = 0.2443

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fifthblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputAir = view.findViewById<EditText>(R.id.inputAir)
        val fifthButton = view.findViewById<Button>(R.id.fifthBlockConfirm)
        val airCheckBox = view.findViewById<CheckBox>(R.id.airCheckBox)
//        val finalAirEmission = inputAir.text.toString().toInt() * AIRCRAFT_COEFFICIENT




        fifthButton.setOnClickListener {
            val inputAirText = inputAir.text.toString();

            if (airCheckBox.isChecked) {
                val sixthBlockFragment = SixthBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, sixthBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            if (inputAirText.isEmpty()) {
                inputAir.setBackgroundResource(R.drawable.spinner_border_red)
                inputAir.hint = "Введите кол-ва часов"
                inputAir.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }


            val sixthBlockFragment = SixthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, sixthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }


}