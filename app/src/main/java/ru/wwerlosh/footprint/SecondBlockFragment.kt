package ru.wwerlosh.footprint

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wwerlosh.footprint.util.GlobalData

class SecondBlockFragment : Fragment() {
    var day: String? = null
    val days = arrayOf("Выберите количество дней","1","2","3","4","5","6","7")


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
        val daysSpinner: Spinner = view.findViewById(R.id.daysSpinner)
        daysSpinner.setBackgroundResource(R.drawable.spinner_border)
        val daysTypeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, days)

        daysSpinner.adapter = daysTypeSpinnerAdapter

        daysSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                day = days[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                day = "Выберите количество дней"
            }

        }




        GlobalData.total = 0.0

        val layout = layoutInflater.inflate(R.layout.toast_layout, requireView().findViewById(R.id.toast_root))
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        val carTypeSpinner: Spinner = view.findViewById(R.id.carTypeSpinner)
        val carTypes = arrayOf(
            "Выберите тип кузова",
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
        val carTypeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, carTypes)
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
        val fuelTypeSpinnerAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, fuelTypes)
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
        val inputMilTextView = view.findViewById<TextView>(R.id.inputMilTextView)
        val inputUsageDaysTextView = view.findViewById<TextView>(R.id.inputUsageDaysTextView)
        val carCheckBox = view.findViewById<CheckBox>(R.id.carCheckBox)
        val secondLay = view.findViewById<LinearLayout>(R.id.secondLay)
        val secondBlockButton = view.findViewById<Button>(R.id.secondBlockButton)

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        carCheckBox.setOnClickListener {
            if (carCheckBox.isChecked) {
                carTypeSpinner.startAnimation(fadeOutAnimation)
                fuelTypeSpinner.startAnimation(fadeOutAnimation)
                secondLay.startAnimation(fadeOutAnimation)
                carTypeSpinner.visibility = View.GONE
                fuelTypeSpinner.visibility = View.GONE
                secondLay.visibility = View.GONE
            }
            else {
                carTypeSpinner.visibility = View.VISIBLE
                fuelTypeSpinner.visibility = View.VISIBLE
                secondLay.visibility = View.VISIBLE
                carTypeSpinner.startAnimation(fadeInAnimation)
                fuelTypeSpinner.startAnimation(fadeInAnimation)
                secondLay.startAnimation(fadeInAnimation)
            }
        }

        secondBlockButton.setOnClickListener {

            if (carCheckBox.isChecked) {
                val thirdBlockFragment = ThirdBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in, // Анимация появления для нового фрагмента
                    R.anim.fade_out, // Анимация затухания для текущего фрагмента
                    R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                    R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
                )
                fragmentTransaction.replace(R.id.fragmentContainer, thirdBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@setOnClickListener
            }

            val mileage = inputMileage.text.toString()
            val usageDays = day
            if (selectedCarType == "Выберите тип автомобиля") {
                toast.show()
                return@setOnClickListener
            }

            if (selectedFuelType == "Выберите тип топлива") {
                toast.show()
                return@setOnClickListener
            }

            // Проверка на пустое поле
            if (mileage.isEmpty()) {
                val text = "*   Какое расстояние (в среднем) вы проезжаете за день? (в км)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 62, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2
                inputMilTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }

            if (usageDays == "Выберите количество дней") {
                val text = "*   Сколько дней в неделю в используете личный транспорт?"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2
                inputUsageDaysTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }
            val emissionCoefficient = carFuelData[selectedCarType]?.get(selectedFuelType)
            if (emissionCoefficient != null) {
                transportEmission = (emissionCoefficient *
                        inputMileage.text.toString().toDouble() *
                        usageDays.toString().toDouble())
            }
            GlobalData.total += transportEmission


            val thirdBlockFragment = ThirdBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in, // Анимация появления для нового фрагмента
                R.anim.fade_out, // Анимация затухания для текущего фрагмента
                R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
            )
            fragmentTransaction.replace(R.id.fragmentContainer, thirdBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

}