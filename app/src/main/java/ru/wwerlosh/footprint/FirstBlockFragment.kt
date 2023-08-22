package ru.wwerlosh.footprint

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.w3c.dom.Text
import ru.wwerlosh.footprint.util.GlobalData

class FirstBlockFragment : Fragment() {
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_firstblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
//
//        val name = sharedPreferences.getString("name", "")
//        val age = sharedPreferences.getInt("age", 0)
//        val town = sharedPreferences.getString("town", "")
//        val sexData = sharedPreferences.getString("sex", "")
//        val totalEmission = sharedPreferences.getFloat("totalEmission", 0.0f)
//
//        if (totalEmission != 0.0f) {
//            GlobalData.total = totalEmission.toDouble()
//            if (name != null) {
//                GlobalData.name = name
//            }
//            GlobalData.age = age
//            if (town != null) {
//                GlobalData.town = town
//            }
//            if (sexData != null) {
//                GlobalData.sex = sexData
//            }
//
//            val eightBlockFragment = EightBlockFragment()
//            val fragmentManager = requireActivity().supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragmentContainer, eightBlockFragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }

        val layout = layoutInflater.inflate(R.layout.toast_layout, requireView().findViewById(R.id.toast_root))
        val inputNameSpace = view.findViewById<EditText>(R.id.inputNameSpace)
        val inputTownSpace = view.findViewById<EditText>(R.id.inputTownSpace)
        val inputAgeSpace = view.findViewById<EditText>(R.id.inputAgeSpace)
        val sexRadioGroup = view.findViewById<RadioGroup>(R.id.sexRadioGroup)
        val sexTextView = view.findViewById<TextView>(R.id.sexTextView)
        var sex: String = "none"

        sexRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.maleSexButton -> {
                    sex = "Мужчина"
                }
                R.id.femaleSexButton -> {
                    sex = "Женщина"
                }
            }
            return@setOnCheckedChangeListener
        }
        val firstInformationConfirmButton = view.findViewById<Button>(R.id.firstInformationConfirmButton)
        firstInformationConfirmButton.setOnClickListener {
            val name = inputNameSpace.text.toString()
            val town = inputTownSpace.text.toString()
            val age = inputAgeSpace.text.toString()

            if (name.isEmpty()) {
                val hintText = "*   Введите ваше имя"
                val spannable = SpannableString(hintText)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                inputNameSpace.hint = spannable
                val toast = Toast(requireContext())
                toast.duration = Toast.LENGTH_SHORT
                toast.view = layout
                toast.show()
                return@setOnClickListener
            }

            if (town.isEmpty()) {
                val hintText = "*   Введите город проживания"
                val spannable = SpannableString(hintText)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                inputTownSpace.hint = spannable
                Toast.makeText(requireContext(), "Не все обязательные поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (age.isEmpty()) {
                val hintText = "*   Введите ваш возраст"
                val spannable = SpannableString(hintText)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                inputAgeSpace.hint = spannable
                Toast.makeText(requireContext(), "Не все обязательные поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (sex == "none") {
                val text = "*   Ваш пол"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2

                sexTextView.text = spannable
                Toast.makeText(requireContext(), "Не все обязательные поля заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalData.name = name
            GlobalData.town = town
            GlobalData.age = age.toInt()
            GlobalData.sex = sex
            val secondBlockFragment = SecondBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, secondBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}