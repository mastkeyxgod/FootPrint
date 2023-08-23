package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wwerlosh.footprint.util.GlobalData

class ThirdBlockFragment : Fragment(){
    final val LOSS_FACTOR = 0.23314
    final val WATER_REMISSION_COEFFICIENT = 0.344

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thirdblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputElectricity = view.findViewById<EditText>(R.id.inputElectro)
        val inputWater = view.findViewById<EditText>(R.id.inputWater)
        val thirdBlockButton = view.findViewById<Button>(R.id.thirdBlockConfirm)

        thirdBlockButton.setOnClickListener {
            val electricity = inputElectricity.text.toString()
            val water = inputWater.text.toString()

            if (electricity.isEmpty()) {
                inputElectricity.setBackgroundResource(R.drawable.spinner_border_red)
                return@setOnClickListener
            } else {
                inputElectricity.setBackgroundResource(R.drawable.spinner_border)
            }
            if (water.isEmpty()) {
                inputWater.setBackgroundResource(R.drawable.spinner_border_red)
                return@setOnClickListener
            } else {
                inputWater.setBackgroundResource(R.drawable.spinner_border)
            }

            val finalElectricity = LOSS_FACTOR * electricity.toInt()
            val finalWaterEmission = WATER_REMISSION_COEFFICIENT * water.toInt()
            val finalEmission = (finalWaterEmission + finalElectricity) / 12

            GlobalData.total += finalEmission

            val fourthBlockFragment = FourthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fourthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}