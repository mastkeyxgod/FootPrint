package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wwerlosh.footprint.util.GlobalData

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
                inputAir.hint = "Введите кол-во часов"
                inputAir.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }
            val finalAirEmission = inputAirText.toInt() * AIRCRAFT_COEFFICIENT

            GlobalData.total += finalAirEmission

            val sixthBlockFragment = SixthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, sixthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }


}