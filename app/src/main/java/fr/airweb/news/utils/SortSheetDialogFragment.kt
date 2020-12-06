package fr.airweb.news.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.airweb.news.databinding.LayoutSortBottomSheetBinding

/**
 * Created by Majdi RABEH on 06/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class SortSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        var TAG = SortSheetDialogFragment::class.java.simpleName
    }

    private lateinit var eventClickListener: EventClickListener
    lateinit var layoutSortBottomSheetBinding: LayoutSortBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layoutSortBottomSheetBinding = LayoutSortBottomSheetBinding.inflate(layoutInflater)
        return layoutSortBottomSheetBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layoutSortBottomSheetBinding.title.setOnClickListener {
            eventClickListener.onTitleClicked()
            dismiss()
        }
        layoutSortBottomSheetBinding.date.setOnClickListener {
            eventClickListener.onDateClicked()
            dismiss()
        }
    }

    fun setEventClickListener(eventClickListener: EventClickListener) {
        this.eventClickListener = eventClickListener
    }

    fun getEventClickListener(): EventClickListener {
        return this.eventClickListener
    }

    interface EventClickListener {
        fun onDateClicked()
        fun onTitleClicked()
    }

}