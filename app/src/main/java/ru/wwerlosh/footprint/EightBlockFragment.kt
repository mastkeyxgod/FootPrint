package ru.wwerlosh.footprint

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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

    private var backButtonPressCount = 0
    private val requiredBackButtonPresses = 2
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
        val layout2 = layoutInflater.inflate(R.layout.toast_exit, requireView().findViewById(R.id.toast_root))
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

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

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
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in, // Анимация появления для нового фрагмента
                R.anim.fade_out, // Анимация затухания для текущего фрагмента
                R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
            )
            fragmentTransaction.replace(R.id.fragmentContainer, secondBlockFragment)
            fragmentTransaction.addToBackStack(null)
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

        val popupFadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val popupFadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        popupView.findViewById<Button>(R.id.closeButton).setOnClickListener {
            popupView.startAnimation(popupFadeOutAnimation) // Применяем анимацию затухания к контенту
            popupFadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    popupWindow.dismiss() // Закрыть всплывающее окно после окончания анимации затухания
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        popupView.startAnimation(popupFadeInAnimation) // Применяем анимацию появления к контенту
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }




}