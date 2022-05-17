package com.example.dungeonans.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import com.example.dungeonans.Activity.UserProfileEditActivity
import com.example.dungeonans.Adapter.SocialLinkSpinnerAdapter
import com.example.dungeonans.DataClass.SpinnerModel
import com.example.dungeonans.R
import com.example.dungeonans.databinding.FragmentLinkEditBinding

class ProfileLinkEditFragment: Fragment() {

    private lateinit var spinnerAdapterLink: SpinnerAdapter
    private val listOfLink = ArrayList<SpinnerModel>()

    private var _binding: FragmentLinkEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileActivity : UserProfileEditActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        profileActivity = activity as UserProfileEditActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinkEditBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.backBtn.setOnClickListener {
            profileActivity.transFragEvent(UserProfileEditFragment())
        }

        setupSpinnerLink()
        setupSpinnerHandler()

        return view
    }

    private fun setupSpinnerLink() {
        val socialLink = resources.getStringArray(R.array.social_array)

        for (i in socialLink.indices) {
            val link = SpinnerModel(R.drawable.user_icon, socialLink[i])
            listOfLink.add(link)
        }

        spinnerAdapterLink = SocialLinkSpinnerAdapter(profileActivity, R.layout.item_spinner_link, listOfLink)
        binding.socialLinkSpinner.adapter = spinnerAdapterLink
    }

    private fun setupSpinnerHandler() {
        binding.socialLinkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val socialLink = binding.socialLinkSpinner.getItemAtPosition(position) as SpinnerModel
                if (socialLink.name != "소셜 플랫폼") {
                    Log.d("TAG", socialLink.name)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}