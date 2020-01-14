package com.lambda_labs.community_calendar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambda_labs.community_calendar.model.RecentSearch
import com.lambda_labs.community_calendar.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.searches_recycler_item.view.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searches_recycler.setHasFixedSize(true)
        searches_recycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        viewModel.getRecentSearches()
        viewModel.searchList.observe(viewLifecycleOwner, Observer<MutableList<RecentSearch>> { recentSearches ->
            searches_recycler.adapter = RecentSearchRecycler(recentSearches)
        })
    }

    inner class RecentSearchRecycler(private val recentSearches: MutableList<RecentSearch>) :
        RecyclerView.Adapter<RecentSearchRecycler.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.searches_recycler_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = recentSearches.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val recentSearch = recentSearches[position]

            holder.searchText.text = recentSearch.searchText
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val searchText: TextView = view.search_txt
        }
    }
}
