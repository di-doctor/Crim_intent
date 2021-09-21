package com.example.crim_intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"


class CrimeListFragment private constructor() : Fragment() {

    private lateinit var crimeRecycleView: RecyclerView
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total Crimes :${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecycleView = view.findViewById(R.id.crime_recycle_view) as RecyclerView
        crimeRecycleView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        val adapter = CrimeAdapter(crimes)
        crimeRecycleView.adapter = adapter
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    "${crime.title} pressed",
                    Toast.LENGTH_LONG
                ).show()
            }
//            itemView.setOnClickListener(object : View.OnClickListener{
//                override fun onClick(p0: View?) {
//                    Toast.makeText(context,"${crime.title} pressed", Toast.LENGTH_LONG).show()
//                }
//            })
        }

        fun bind(crime_param: Crime, needPolice: Boolean) {
            crime = crime_param
            titleTextView.text = crime.title
            if (needPolice) dateTextView.text = "NEED POLICE"
            else dateTextView.text = crime.date.toString()
        }
//        override fun onClick(p0: View?) {
//            Toast.makeText(context,"${crime.title} pressed",Toast.LENGTH_LONG).show()
//        }
    }

    private inner class CrimeAdapter(crimes_param: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        private val NEEDPOLICE = 1
        private val INDEPENDENTLY = 0
        var crimes = crimes_param

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = when (viewType) {
                INDEPENDENTLY -> layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                NEEDPOLICE -> layoutInflater.inflate(R.layout.list_item_crime_police, parent, false)
                else -> layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            }
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            val needPolice: Boolean = crime.requiresPolice
            holder.bind(crime, needPolice)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun getItemViewType(position: Int): Int {
            val crime = crimes[position]
            return if (crime.requiresPolice) NEEDPOLICE
            else INDEPENDENTLY
        }
    }
}
