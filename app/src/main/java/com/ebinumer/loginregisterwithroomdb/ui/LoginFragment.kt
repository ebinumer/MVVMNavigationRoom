package com.ebinumer.loginregisterwithroomdb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ebinumer.loginregisterwithroomdb.R
import com.ebinumer.loginregisterwithroomdb.data.Database.RegisterDatabase
import com.ebinumer.loginregisterwithroomdb.data.Database.RegisterRepository
import com.ebinumer.loginregisterwithroomdb.databinding.FragmentLoginBinding
import com.ebinumer.loginregisterwithroomdb.factory.LoginViewModelFactory
import com.ebinumer.loginregisterwithroomdb.utils.SessionManager
import com.ebinumer.loginregisterwithroomdb.utils.showToast
import com.ebinumer.loginregisterwithroomdb.viewModel.LoginViewModel

class LoginFragment : Fragment() {
    lateinit var mViewModel: LoginViewModel
    lateinit var mBinding: FragmentLoginBinding
    lateinit var mFactory: LoginViewModelFactory
    lateinit var mSessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val repository = RegisterRepository(dao)
        mSessionManager = SessionManager(requireContext())
        mFactory = LoginViewModelFactory(repository, application)
        if (mSessionManager.appOpenStatus) {
            navigateUserDetails()
        }
        mViewModel = ViewModelProvider(this, mFactory)[LoginViewModel::class.java]
        mBinding.apply {
            myLoginViewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
        }


        mViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                Log.e("MYTAG", "insidi observe")
                signUp()
                mViewModel.doneNavigatingRegiter()
            }
        })

        mViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError) {
                "Please fill all fields".showToast(requireContext())
                mViewModel.donetoast()
            }
        })

        mViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError) {
                "User doesnt exist,please Register!".showToast(requireContext())
                mViewModel.donetoastErrorUsername()
            }
        })

        mViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError) {
                "Please check your Password".showToast(requireContext())
                mViewModel.donetoastInvalidPassword()
            }
        })

        mViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                Log.i("MYTAG", "insidi observe")
                mSessionManager.appOpenStatus = true
                navigateUserDetails()
                mViewModel.doneNavigatingUserDetails()
            }
        })


    }

    private fun signUp() {
        Log.i("MYTAG", "insidisplayUsersList")
        NavHostFragment.findNavController(this)
            .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

    }

    private fun navigateUserDetails() {
        Log.i("MYTAG", "insidisplayUsersList")
        NavHostFragment.findNavController(this)
            .navigate(LoginFragmentDirections.actionLoginFragmentToUserDetailsFragment())
    }
}