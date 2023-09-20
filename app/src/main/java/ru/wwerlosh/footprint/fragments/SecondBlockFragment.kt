    package ru.wwerlosh.footprint.fragments

    import android.graphics.Color
    import android.os.Bundle
    import android.os.CountDownTimer
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
    import androidx.activity.OnBackPressedCallback
    import androidx.fragment.app.Fragment
    import ru.wwerlosh.footprint.R
    import ru.wwerlosh.footprint.util.CarFuelDB
    import ru.wwerlosh.footprint.util.GlobalData

    class SecondBlockFragment : Fragment() {
        private val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        private val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        private var backButtonPressCount = 0
        private val requiredBackButtonPresses = 2
        var day: String? = null
        val days = arrayOf("Выберите количество дней","1","2","3","4","5","6","7")

        private val carFuelDB = CarFuelDB()
        private val carFuelData = carFuelDB.getData()

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
            val layout2 = layoutInflater.inflate(R.layout.toast_exit, requireView().findViewById(R.id.toast_exit_root))
            val toastExit = Toast(requireContext())
            toastExit.duration = Toast.LENGTH_SHORT
            toastExit.view = layout2
            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val timer = object: CountDownTimer(5000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}

                        override fun onFinish() {
                            backButtonPressCount = 0
                        }
                    }
                    timer.start()
                    if (backButtonPressCount < requiredBackButtonPresses - 1) {
                        backButtonPressCount++
                        toastExit.show()
                    } else {
                        isEnabled = false
                        requireActivity().finish()
                    }
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

            val daysSpinner: Spinner = view.findViewById(R.id.daysSpinner)
            daysSpinner.setBackgroundResource(R.drawable.spinner_border)
            val daysTypeSpinnerAdapter = ArrayAdapter(requireContext(),
                R.layout.spinner_layout, days)

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
            val carTypes = carFuelDB.getCarTypes()
            val carTypeSpinnerAdapter = ArrayAdapter(requireContext(),
                R.layout.spinner_layout, carTypes)
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
            val fuelTypes = carFuelDB.getFuelTypes()
            val fuelTypeSpinnerAdapter = ArrayAdapter(requireContext(),
                R.layout.spinner_layout, fuelTypes)
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
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
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

                    spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(colorSpan2, 2, 62, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    inputMilTextView.text = spannable
                    toast.show()
                    return@setOnClickListener
                }

                if (usageDays == "Выберите количество дней") {
                    val text = "*   Сколько дней в неделю в используете личный транспорт?"
                    val spannable = SpannableString(text)

                    val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54))
                    val colorSpan2 = ForegroundColorSpan(Color.WHITE)

                    spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(colorSpan2, 2, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                fragmentTransaction.replace(R.id.fragmentContainer, thirdBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }

    }