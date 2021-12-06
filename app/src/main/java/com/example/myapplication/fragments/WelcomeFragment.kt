package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.helper.cancelDialog
import com.example.myapplication.helper.showLoader
import com.example.myapplication.viewmodel.GiphyState
import com.example.myapplication.viewmodel.GiphyViewModel

class WelcomeFragment : Fragment() {

    lateinit var binding: MainFragmentBinding
    lateinit var giphyViewModel: GiphyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        giphyViewModel = ViewModelProvider(this).get(GiphyViewModel::class.java)

        binding.tvLogout.setOnClickListener {
            (requireActivity() as MainActivity).logoutSuccessful()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        giphyViewModel.getGiphy()
        giphyViewModel.setUsername(arguments?.getString(KEY_USERNAME) ?: "")
    }

    override fun onResume() {
        super.onResume()
        giphyViewModel.giphyState.observe(viewLifecycleOwner, {
            when (it) {
                GiphyState.Idle -> {
                    cancelDialog()
                }
                GiphyState.Loading -> {
                    requireActivity().showLoader()
                }
                is GiphyState.Success -> {
                    cancelDialog()
                    Glide.with(this).load(it.url).into(binding.ivGif)
                    binding.tvTitle.text = it.title
                    binding.tvAuthor.text = it.author
                }
                GiphyState.Error -> {
                    cancelDialog()
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        giphyViewModel.username.observe(viewLifecycleOwner, {
            binding.tvWelcome.text = "Welcome $it"
        })
    }

    companion object {
        const val KEY_USERNAME = "username"

        fun newInstance(username: String): WelcomeFragment {
            val fragment = WelcomeFragment()
            fragment.arguments = Bundle().apply { putString(KEY_USERNAME, username) }
            return fragment
        }
    }

}