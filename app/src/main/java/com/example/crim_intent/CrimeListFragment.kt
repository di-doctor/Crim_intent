package com.example.crim_intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crim_intent.R.id.crime_solved_image

private const val TAG = "CrimeListFragment"


class CrimeListFragment : Fragment() {
    private val NEEDPOLICE = 1
    private val INDEPENDENTLY = 0

    private lateinit var crimeRecycleView: RecyclerView
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecycleView = view.findViewById(R.id.crime_recycle_view) as RecyclerView //JETPACK VIEWBINDER
        crimeRecycleView.layoutManager = LinearLayoutManager(context)
        crimeRecycleView.adapter = adapter
        return view
    }

    private fun updateUI(crimes: List<Crime>) {
        val adapter = CrimeAdapter(crimes)
        crimeRecycleView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.d(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var crime: Crime //для кеширования
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(crime_solved_image)

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

        fun bind(crime_param: Crime) {
            crime = crime_param
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private inner class CrimeAdapter(crimes_param: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        var crimes = crimes_param

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }
    }
}

