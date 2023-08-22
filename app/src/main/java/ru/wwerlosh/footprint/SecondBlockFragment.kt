package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wwerlosh.footprint.util.GlobalData

class SecondBlockFragment : Fragment() {

    private val carFuelData = mapOf(
        "Седан" to mapOf("Дизель" to 0.20419, "Бензин" to 0.27807, "Смешанное топливо" to 0.1448, "Электричество" to 0.0),
        "Пикап" to mapOf("Дизель" to 0.20419, "Бензин" to 0.27807, "Смешанное топливо" to 0.1448, "Электричество" to 0.0),
        "Хэтчбек" to mapOf("Дизель" to 0.16637, "Бензин" to 0.18659, "Смешанное топливо" to 0.10698, "Электричество" to 0.0),
        "Универсал" to mapOf("Дизель" to 0.16637, "Бензин" to 0.18659, "Смешанное топливо" to 0.10698, "Электричество" to 0.0),
        "Лифтбек" to mapOf("Дизель" to 0.16637, "Бензин" to 0.18659, "Смешанное топливо" to 0.10698, "Электричество" to 0.0),
        "Минивен" to mapOf("Дизель" to 0.13721, "Бензин" to 0.14836, "Смешанное топливо" to 0.10275, "Электричество" to 0.0),
        "Купе" to mapOf("Дизель" to 0.20419, "Бензин" to 0.27807, "Смешанное топливо" to 0.1448, "Электричество" to 0.0),
        "Кроссовер" to mapOf("Дизель" to 0.16637, "Бензин" to 0.18659, "Смешанное топливо" to 0.10698, "Электричество" to 0.0),
        "Мотоцикл" to mapOf("Дизель" to 0.0, "Бензин" to 0.08277, "Смешанное топливо" to 0.0, "Электричество" to 0.0)
    )

    var transportEmission = 0.0
    var selectedFuelType: String? = null
    var selectedCarType: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_secondblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carTypeSpinner: Spinner = view.findViewById(R.id.carTypeSpinner)
        val carTypes = arrayOf(
            "Выберите тип автомобиля",
            "Седан",
            "Пикап",
            "Хэтчбек",
            "Универсал",
            "Лифтбек",
            "Минивен",
            "Купе",
            "Кроссовер",
            "Мотоцикл"
        )
        val carTypeSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, carTypes)
        carTypeSpinner.adapter = carTypeSpinnerAdapter
        carTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCarType = carTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCarType = "Выберите тип автомобиля"
            }
        }

        val fuelTypeSpinner: Spinner = view.findViewById(R.id.fuelTypeSpinner)
        val fuelTypes = arrayOf(
            "Выберите тип топлива",
            "Дизель",
            "Бензин",
            "Смешанное топливо",
            "Электричество"
        )
        val fuelTypeSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fuelTypes)
        fuelTypeSpinner.adapter = fuelTypeSpinnerAdapter
        fuelTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFuelType = fuelTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedFuelType = "Выберите тип топлива"
            }
        }
        val inputMileage = view.findViewById<EditText>(R.id.inputMileage)
        val inputUsageDays = view.findViewById<EditText>(R.id.inputUsageDays)
        val carCheckBox = view.findViewById<CheckBox>(R.id.carCheckBox)

        val secondBlockButton = view.findViewById<Button>(R.id.secondBlockButton)
        secondBlockButton.setOnClickListener {
            if (carCheckBox.isChecked) {
                val thirdBlockFragment = ThirdBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, thirdBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@setOnClickListener
            }
            val mileage = inputMileage.text.toString()
            val usageDays = inputUsageDays.text.toString()
            if (selectedCarType == "Выберите тип автомобиля") {
                return@setOnClickListener
            }

            if (selectedFuelType == "Выберите тип топлива") {
                return@setOnClickListener
            }

            // Проверка на пустое поле
            if (mileage.isEmpty()) {
                return@setOnClickListener
            }

            if (usageDays.isEmpty()) {
                return@setOnClickListener
            }
            val emissionCoefficient = carFuelData[selectedCarType]?.get(selectedFuelType)
            if (emissionCoefficient != null) {
                transportEmission = (emissionCoefficient *
                        inputMileage.text.toString().toDouble() *
                        inputUsageDays.text.toString().toDouble())
            }
            GlobalData.total += transportEmission


            val thirdBlockFragment = ThirdBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, thirdBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

}