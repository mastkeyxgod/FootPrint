package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class SixthBlockFragment : Fragment() {
    final val TRAIN_COEFFICIENT = 0.03694
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sixthblock, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sixthButton = view.findViewById<Button>(R.id.sixthBlockConfirm)
        val inputTrain = view.findViewById<EditText>(R.id.inputTrain)
        val trainCheckBox = view.findViewById<CheckBox>(R.id.trainCheckBox)

        sixthButton.setOnClickListener {
            val inputTrainText = inputTrain.text.toString()

            if (trainCheckBox.isChecked) {
                val seventhBlockFragment = SeventhBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, seventhBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            if (inputTrainText.isEmpty()) {
                inputTrain.setBackgroundResource(R.drawable.spinner_border_red)
                inputTrain.hint = "Введите кол-ва часов"
                inputTrain.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                return@setOnClickListener
            }
            val seventhBlockFragment = SeventhBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, seventhBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}