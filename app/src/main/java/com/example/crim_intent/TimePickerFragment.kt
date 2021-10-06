package com.example.crim_intent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_TIME = "time"

class TimePickerFragment : DialogFragment() {

    interface CallBacks {
        fun onTimeSelected(date: Date)
    }

    lateinit var date: Date
    private val calendar: Calendar by lazy {
        Calendar.getInstance()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        date = arguments?.getSerializable(ARG_TIME) as Date
        calendar.time = date
        val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMinute = calendar.get(Calendar.MINUTE)

        val timeListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                val resultTime = GregorianCalendar(year, month, dayOfMonth,hour,minute).time
                targetFragment?.let { fragment ->
                    (fragment as CallBacks).onTimeSelected(resultTime)
                }
            }
        return TimePickerDialog(requireContext(), timeListener, initialHour, initialMinute, true)
    }

    companion object {
        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}