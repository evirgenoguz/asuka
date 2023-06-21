package com.evirgenoguz.asuka.ui.features.login

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.evirgenoguz.asuka.core.BaseFragment
import com.evirgenoguz.asuka.data.NetworkResult
import com.evirgenoguz.asuka.data.model.request.LoginRequest
import com.evirgenoguz.asuka.databinding.FragmentLoginBinding
import com.evirgenoguz.asuka.ext.observeLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel by viewModels<LoginViewModel>()

    override fun setupUi() {
        initListeners()
        observeLoginLiveData()
        observeHealthyLiveData()
    }

    private fun observeLoginLiveData() {
        lifecycleScope.launchWhenStarted {
            observeLiveData(viewModel.login) {
                when (it) {
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "Deneme")
                    }

                    is NetworkResult.Success -> {

                        // TODO: navigate inside the app with uid
                        Toast.makeText(
                            context,
                            "$it successfully login",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is NetworkResult.Error -> {
                        Log.e(TAG, it.error.message)
                    }
                }
            }
        }
    }

    private fun observeHealthyLiveData() {
        lifecycleScope.launchWhenStarted {
            observeLiveData(viewModel.healthy) {
                when (it) {
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "Deneme")
                    }

                    is NetworkResult.Success -> {

                        // TODO: navigate inside the app with uid
                        Toast.makeText(
                            context,
                            "${it.body} successfully login",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is NetworkResult.Error -> {
                        Log.e(TAG, it.error.message)
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.login(LoginRequest("abdullah", "123"))
//            viewModel.isHealthy()
        }
    }


}