package ru.wwerlosh.footprint

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.wwerlosh.footprint.data.User
import ru.wwerlosh.footprint.util.GlobalData

class EightBlockFragment : Fragment() {

    private val AVG_VALUE: Double = 44.08

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eigthblock, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", GlobalData.name)
        editor.putInt("age", GlobalData.age)
        editor.putString("town", GlobalData.town)
        editor.putString("sex", GlobalData.sex)
        editor.putFloat("totalEmission", GlobalData.total.toFloat())
        editor.apply()

        val conclusionTextView = view.findViewById<TextView>(R.id.conclusionTextView)
        val conclusionButton = view.findViewById<Button>(R.id.conclusionButton)
        val lastBlockConfirm = view.findViewById<Button>(R.id.lastBlockConfirm)
        val totalEmissionTextView = view.findViewById<TextView>(R.id.totalEmissionTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)

        val formattedTotal = String.format("%.1f", GlobalData.total)
        totalEmissionTextView.text = formattedTotal
        nameTextView.text = GlobalData.name

        if (GlobalData.total > AVG_VALUE) {
            conclusionTextView.text = resources.getString(R.string.bad_conclusion)
        }
        else conclusionTextView.text = resources.getString(R.string.good_conclusion)

        conclusionButton.setOnClickListener {
            showPopupMessage(view)
        }



        if(!GlobalData.isDataSaved) {
            println("YES")
            writeToDatabase()
            GlobalData.isDataSaved = true
        }


        lastBlockConfirm.setOnClickListener {
            val secondBlockFragment = SecondBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, secondBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentManager.popBackStack(R.layout.fragment_eigthblock, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fragmentTransaction.commit()
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun writeToDatabase() {
        val user = User(GlobalData.total, GlobalData.name, GlobalData.town, GlobalData.age, GlobalData.sex)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {

                SQLHelper.connection(user)
            }
        }
    }

    fun showPopupMessage(view: View) {
        val popupView = LayoutInflater.from(requireContext()).inflate(R.layout.conclusion_layout, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)

        popupView.findViewById<Button>(R.id.closeButton).setOnClickListener {
            popupWindow.dismiss() // Закрыть всплывающее окно по нажатию на кнопку
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }



}