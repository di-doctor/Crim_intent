package com.example.crim_intent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {
    interface CallBacks{
        fun onDateSelected(date: Date)
    }

    val calendar: Calendar by lazy {
        Calendar.getInstance()
    }
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val date  = arguments?.getSerializable(ARG_DATE) as Date
            calendar.time = date
            val initialYear = calendar.get(Calendar.YEAR)
            val initialMonth = calendar.get(Calendar.MONTH)
            val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

            val initialHour = calendar.get(Calendar.HOUR)
            val initialMinutes = calendar.get(Calendar.MINUTE)

            val dateListener = DatePickerDialog.OnDateSetListener{
                    _: DatePicker, year: Int, month: Int, day: Int->
                val resultDate = GregorianCalendar(year,month,day,initialHour,initialMinutes).time
                targetFragment?.let { fragment->
                    (fragment as CallBacks).onDateSelected(resultDate)
                }
            }




        return DatePickerDialog(requireContext(), dateListener, initialYear, initialMonth, initialDay)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}