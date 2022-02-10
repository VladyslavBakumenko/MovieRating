package com.example.movierating.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.movierating.databinding.FragmentWebLoginBinding

class WebLoginFragment : Fragment() {

    private val viewModel: WebLoginFragmentViewModel by viewModels()

    private var binding: FragmentWebLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPermissionUrl()


        viewModel.url.observe(viewLifecycleOwner, Observer {
            binding?.webView?.loadUrl(it.toString())
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    companion object {
        fun newInstance(): WebLoginFragment = WebLoginFragment()
    }

}