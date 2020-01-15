package com.lambda_labs.community_calendar

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.chip.Chip
import com.lambda_labs.community_calendar.util.DatePickerFragment
import com.lambda_labs.community_calendar.util.Util
import com.lambda_labs.community_calendar.util.ViewUtil
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_view_fragment_filter_cancel.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        // Populate list of locations in the Spinner View
        ArrayAdapter.createFromResource(
            view.context,
            R.array.filter_locations_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_fragment_filter_location.adapter = arrayAdapter
        }

        // Show the Date Picker when the date control is engaged
        text_view_fragment_filter_date_shown.text = Util.getSearchDate(Util.getToday())
        image_view_fragment_filter_date.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(fragmentManager!!, "datePicker")
        }

        // Store the display attributes of the screen for calculations
        val displayMetrics: DisplayMetrics = view.context.applicationContext.resources.displayMetrics
        val px: Int = ViewUtil.dpToPx(40, displayMetrics)

        // Populate the initial chip tags to be added to the included group
        resources.getStringArray(R.array.tags_added_array).forEachIndexed { index, tagText ->
            val chip: Chip = ViewUtil.generateChip(context!!, true, px)
            chip.text = tagText
            chip.id = index
            chip_group_fragment_filter_added.addView(chip)
        }

        // Populate the initial chip tags to be added to the suggested group
        resources.getStringArray(R.array.tags_suggested_array).forEachIndexed { index, tagText ->
            val chip: Chip = ViewUtil.generateChip(context!!, false, px)
            chip.text = tagText
            chip.id = index
            chip_group_fragment_filter_suggested.addView(chip)
        }
    }
}
