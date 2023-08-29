package ru.wwerlosh.footprint

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.wwerlosh.footprint.util.GlobalData

class ThirdBlockFragment : Fragment(){

    private var backButtonPressCount = 0
    private val requiredBackButtonPresses = 2

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
        val layout2 = layoutInflater.inflate(R.layout.toast_exit, requireView().findViewById(R.id.toast_root))
        val toastExit = Toast(requireContext())
        toastExit.duration = Toast.LENGTH_SHORT
        toastExit.view = layout2
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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

        val layout = layoutInflater.inflate(R.layout.toast_layout, requireView().findViewById(R.id.toast_root))
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        val electricityTextView = view.findViewById<TextView>(R.id.electricityTextView)
        val waterTextView = view.findViewById<TextView>(R.id.waterTextView)
        val inputElectricity = view.findViewById<EditText>(R.id.inputElectro)
        val inputWater = view.findViewById<EditText>(R.id.inputWater)
        val thirdBlockButton = view.findViewById<Button>(R.id.thirdBlockConfirm)

        thirdBlockButton.setOnClickListener {
            val electricity = inputElectricity.text.toString()
            val water = inputWater.text.toString()

            if (electricity.isEmpty()) {
                val text = "*   Сколько электричества вы потребляете в год? (в кВт)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                electricityTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }
            if (water.isEmpty()) {
                val text = "*   Сколько воды вы используете в год? (в кубометрах)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 53, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                waterTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }

            val finalElectricity = LOSS_FACTOR * electricity.toFloat()
            val finalWaterEmission = WATER_REMISSION_COEFFICIENT * water.toFloat()
            val finalEmission = (finalWaterEmission + finalElectricity)

            GlobalData.total += finalEmission

            val fourthBlockFragment = FourthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in, // Анимация появления для нового фрагмента
                R.anim.fade_out, // Анимация затухания для текущего фрагмента
                R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
            )
            fragmentTransaction.replace(R.id.fragmentContainer, fourthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}