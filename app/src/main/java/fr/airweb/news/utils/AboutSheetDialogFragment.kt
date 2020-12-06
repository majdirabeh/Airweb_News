package fr.airweb.news.utils

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.airweb.news.Constants.Companion.LATITUDE_AIR_WEB
import fr.airweb.news.Constants.Companion.LONGITUDE_AIR_WEB
import fr.airweb.news.R
import fr.airweb.news.databinding.LayoutAboutBottomSheetBinding
import fr.airweb.news.databinding.LayoutFilterBottomSheetBinding
import java.util.ArrayList

/**
 * Created by Majdi RABEH on 06/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
class AboutSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        var TAG = AboutSheetDialogFragment::class.java.simpleName
    }

    lateinit var layoutAboutBottomSheetBinding: LayoutAboutBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layoutAboutBottomSheetBinding = LayoutAboutBottomSheetBinding.inflate(layoutInflater)

        return layoutAboutBottomSheetBinding.root
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 1000
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layoutAboutBottomSheetBinding.addressText.setOnClickListener {
            navigateToMap()
        }
        layoutAboutBottomSheetBinding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun navigateToMap() {
        val intentUri =
            Uri.parse("google.navigation:q=$LATITUDE_AIR_WEB,$LONGITUDE_AIR_WEB&mode=d")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}