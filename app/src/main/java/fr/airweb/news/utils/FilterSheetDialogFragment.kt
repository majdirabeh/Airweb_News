package fr.airweb.news.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.airweb.news.databinding.LayoutFilterBottomSheetBinding
import java.util.ArrayList

/**
 * Created by Majdi RABEH on 06/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class FilterSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        var TAG = FilterSheetDialogFragment::class.java.simpleName
    }

    private lateinit var eventClickListener: EventClickListener
    lateinit var layoutFilterSheetBinding: LayoutFilterBottomSheetBinding
    var selectedType = hashMapOf<String, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layoutFilterSheetBinding = LayoutFilterBottomSheetBinding.inflate(layoutInflater)
        return layoutFilterSheetBinding.root
    }

    fun setFiltersValue(listTypes: HashMap<String, Boolean>) {
        selectedType = listTypes
    }

    fun getFiltersValue(): HashMap<String, Boolean> {
        return selectedType
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        layoutFilterSheetBinding.news.isChecked = selectedType["news"]!!
        layoutFilterSheetBinding.hot.isChecked = selectedType["hot"]!!
        layoutFilterSheetBinding.actualite.isChecked = selectedType["actualité"]!!

        layoutFilterSheetBinding.news.setOnCheckedChangeListener { _, isChecked ->
            selectedType["news"] = isChecked
        }
        layoutFilterSheetBinding.hot.setOnCheckedChangeListener { _, isChecked ->
            selectedType["hot"] = isChecked
        }
        layoutFilterSheetBinding.actualite.setOnCheckedChangeListener { _, isChecked ->
            selectedType["actualité"] = isChecked
        }
        layoutFilterSheetBinding.submit.setOnClickListener {
            eventClickListener.onSubmitFilterClicked(selectedType)
            dismiss()
        }
    }

    fun setEventClickListener(
        eventClickListener: EventClickListener,
        listTypes: HashMap<String, Boolean>
    ) {
        this.selectedType = listTypes
        this.eventClickListener = eventClickListener
    }

    fun getEventClickListener(): EventClickListener {
        return this.eventClickListener
    }

    interface EventClickListener {
        fun onSubmitFilterClicked(listType: HashMap<String, Boolean>)
    }

}