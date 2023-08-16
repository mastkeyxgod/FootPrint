package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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

        // Получение ссылок на View-элементы вашего макета
        val inputNameSpace = view.findViewById<EditText>(R.id.inputNameSpace)
        val inputTownSpace = view.findViewById<EditText>(R.id.inputTownSpace)
        val inputAgeSpace = view.findViewById<EditText>(R.id.inputAgeSpace)
        val sexRadioGroup = view.findViewById<RadioGroup>(R.id.sexRadioGroup)
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
        }
        val firstInformationConfirmButton = view.findViewById<Button>(R.id.firstInformationConfirmButton)
        firstInformationConfirmButton.setOnClickListener {
            val name = inputNameSpace.text.toString()
            val town = inputTownSpace.text.toString()
            val age = inputAgeSpace.text.toString()

            if (name.isEmpty()) {
                inputNameSpace.setBackgroundResource(R.drawable.spinner_border_red)
                inputNameSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }
            else {
                inputNameSpace.setBackgroundResource(R.drawable.spinner_border)
                inputNameSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            if (town.isEmpty()) {
                inputTownSpace.setBackgroundResource(R.drawable.spinner_border_red)
                inputTownSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }
            else {
                inputTownSpace.setBackgroundResource(R.drawable.spinner_border)
                inputTownSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            if (age.isEmpty()) {
                inputAgeSpace.setBackgroundResource(R.drawable.spinner_border_red)
                inputAgeSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }
            else {
                inputAgeSpace.setBackgroundResource(R.drawable.spinner_border)
                inputAgeSpace.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            if (sex == "none") {
                sexRadioGroup.setBackgroundResource(R.drawable.spinner_border_red)
                return@setOnClickListener
            }
            else {
                sexRadioGroup.setBackgroundColor(R.drawable.spinner_border)
            }
            GlobalData.name = name
            GlobalData.town = town
            GlobalData.age = age
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