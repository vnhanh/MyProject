package com.tma.movemind.ui.fragment_search_videos.children.instructor

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.tma.movemind.R
import kotlinx.android.synthetic.main.dialog_fragment_info_instructor_representation.*

class InfoInstructorRepresentationDialogFragment : DialogFragment() {
    companion object {
        val TAG = this::class.java.simpleName
        val KEY_NAME = "NAME"
        val KEY_DESCRIPTION = "DESCRIPTION"

        fun getInstance(name: String): InfoInstructorRepresentationDialogFragment {
            val fragment = InfoInstructorRepresentationDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_NAME, name)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_info_instructor_representation, container, false)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        setupUI()
    }

    private fun setupUI() {
        arguments?.also { bundle ->
            if(bundle.containsKey(KEY_NAME)){
                val name = bundle.getString(KEY_NAME)
                tv_instructor_name.text = name
            }
        }
    }
}