package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class FourthBlockFragment : Fragment(){

    final val TAXI_COEFFICIENT = 0.20369
    final val BUS_COEFFICIENT = 0.1195
    var selectedCarType: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fourthblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val inputMileage = view.findViewById<EditText>(R.id.inputPublicCarMileage)
        val inputUsageDays = view.findViewById<EditText>(R.id.inputPublicCarUsageDays)

        super.onViewCreated(view, savedInstanceState)
        val carTypeSpinner: Spinner = view.findViewById(R.id.publicCarTypeSpinner)
        carTypeSpinner.setBackgroundResource(R.drawable.spinner_border)
        val carTypes = arrayOf(
            "Выберите тип транспорта",
            "Такси",
            "Автобус"
        )
        val carTypeSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, carTypes)
        carTypeSpinner.adapter = carTypeSpinnerAdapter
        carTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCarType = carTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCarType = "Выберите тип транспорта"
            }
        }

        val fourthBlockButton = view.findViewById<Button>(R.id.fourthBlockButton)
        fourthBlockButton.setOnClickListener {
            val mileage = inputMileage.text.toString()
            val usageDays = inputUsageDays.text.toString()
            if (selectedCarType == "Выберите тип транспорта") {
                carTypeSpinner.setBackgroundResource(R.drawable.spinner_border_red)
                carTypeSpinner.prompt = "Выберите тип автомобиля"
                return@setOnClickListener
            } else {
                carTypeSpinner.setBackgroundResource(R.drawable.spinner_border)
                carTypeSpinner.prompt = null
            }

            // Проверка на пустое поле
            if (mileage.isEmpty()) {
                inputMileage.setBackgroundResource(R.drawable.spinner_border_red)
                inputMileage.hint = "Введите пробег"
                inputMileage.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            } else {
                inputMileage.setBackgroundResource(R.drawable.spinner_border)
                inputMileage.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            if (usageDays.isEmpty()) {
                inputUsageDays.setBackgroundResource(R.drawable.spinner_border_red)
                inputUsageDays.hint = "Введите количество дней"
                inputMileage.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            } else {
                inputUsageDays.setBackgroundResource(R.drawable.spinner_border)
                inputMileage.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            val transportEmission: Double
            val emissionCoefficient: Double = if (selectedCarType == "Такси") 0.20369 else 0.1195
            transportEmission = (emissionCoefficient *
                    inputMileage.text.toString().toDouble() *
                    inputUsageDays.text.toString().toDouble())
            println(transportEmission)
            val fifthBlockFragment = FifthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fifthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}