package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class ThirdBlockFragment : Fragment(){
    final val LOSSFACTOR = 0.23314
    final val EMISSIONCOEFFICIENT = 0.344
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

//        val finalElectricity = EM
        val finalWaterEmission = EMISSIONCOEFFICIENT * inputWater.text.toString().toInt()

        thirdBlockButton.setOnClickListener {

            val fourthBlockFragment = FourthBlockFragment()

            val fragmentManager = requireActivity().supportFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragmentContainer, fourthBlockFragment)

            fragmentTransaction.addToBackStack(null)

            fragmentTransaction.commit()

        }
    }
}