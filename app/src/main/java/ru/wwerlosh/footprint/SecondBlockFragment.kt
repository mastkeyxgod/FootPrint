package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class SecondBlockFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_secondblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.carTypeSpinner)
        spinner.setBackgroundResource(R.drawable.spinner_border)
        val types = arrayOf(
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
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedColor = types[position]
                if (selectedColor == "Выберите тип автомобиля") {
                    // Это специальный элемент с текстом-подсказкой, игнорируем выбор
                } else {
                    // Обработка выбора другого элемента
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ничего не делаем, так как это обработчик события "ничего не выбрано"
            }
        }
    }
}