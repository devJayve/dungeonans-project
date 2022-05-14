package com.example.dungeonans.Fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dungeonans.Activity.SettingActivity
import com.example.dungeonans.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.profilepage_bottom_drawer, container, false)

        var settingLayout : ConstraintLayout = view.findViewById(R.id.settingLayout)
        settingLayout.setOnClickListener{
            var intent = Intent(context,SettingActivity::class.java)
            intent.putExtra("key","0")
            startActivity(intent)
        }
        var securityLayout : ConstraintLayout = view.findViewById(R.id.securityLayout)
        securityLayout.setOnClickListener{
            var intent = Intent(context,SettingActivity::class.java)
            intent.putExtra("key","1")
            startActivity(intent)
        }
        var accountLayout : ConstraintLayout = view.findViewById(R.id.accountLayout)
        accountLayout.setOnClickListener{
            var intent = Intent(context,SettingActivity::class.java)
            intent.putExtra("key","2")
            startActivity(intent)
        }
        var appInfoLayout : ConstraintLayout = view.findViewById(R.id.appInfoLayout)
        appInfoLayout.setOnClickListener{
            var intent = Intent(context,SettingActivity::class.java)
            intent.putExtra("key","3")
            startActivity(intent)
        }
        return view
    }

    companion object {
        const val TAG = "BottomSheetFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 40 / 100
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}