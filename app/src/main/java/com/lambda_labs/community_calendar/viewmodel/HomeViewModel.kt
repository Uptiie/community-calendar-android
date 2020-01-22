package com.lambda_labs.community_calendar.viewmodel

import EventsQuery
import android.util.TypedValue
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.lambda_labs.community_calendar.App
import com.lambda_labs.community_calendar.R
import com.lambda_labs.community_calendar.Repository
import com.lambda_labs.community_calendar.view.MainActivity

class HomeViewModel(val repo: Repository): ViewModel() {



    fun getAllEvents(): LiveData<List<EventsQuery.Event>> {
        return repo.events
    }

    fun setupRecyclers(orientation: Int, activity: FragmentActivity?, recycler: RecyclerView){
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity, orientation, false)
    }

    fun selectListView(mainActivity: MainActivity, gridBtn: ImageView, listBtn: ImageView){
        listBtn.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.list_view_selected))
        gridBtn.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.grid_view_unselected))
    }

    fun selectGridView(mainActivity: MainActivity, gridBtn: ImageView, listBtn: ImageView){
        listBtn.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.list_view_unselected))
        gridBtn.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.grid_view_selected))
    }

    fun setupSearchBarConstraints(parent: ConstraintLayout, search: SearchView, textView: MaterialTextView){
        val dpToPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13f, parent.context.resources.displayMetrics).toInt()

        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)
        constraintSet.connect(search.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        constraintSet.connect(search.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dpToPx)
        constraintSet.connect(search.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        constraintSet.connect(textView.id, ConstraintSet.TOP, search.id, ConstraintSet.BOTTOM)
        constraintSet.applyTo(parent)
    }
}