package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import id.dimas.challenge5.databinding.FragmentLoginBinding
import id.dimas.challenge5.helper.SharedPref
import id.dimas.challenge5.helper.UserRepo
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }

    private val viewModel: LoginViewModel by viewModelsFactory { LoginViewModel(userRepo) }

    private val sharedPref: SharedPref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        moveToRegist(view)
        loginFunction()
        observeData()
    }

    private fun moveToRegist(view: View) {
        val tvRegister = view.findViewById<TextView>(R.id.tv_go_to_register)

        tvRegister.setOnClickListener {
            binding.apply {
                etEmail.text.clear()
                etPassword.text.clear()
                it.findNavController()
                    .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun loginFunction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                when {
                    email.isEmpty() -> {
                        etEmail.error = "Email Tidak Boleh Kosong"
                    }
                    password.isEmpty() -> {
                        etPassword.error = "Password Tidak Boleh Kosong"
                    }
                    else -> {
                        viewModel.checkUserFromDb(email, password)
                    }
                }
            }
        }
    }


    private fun observeData() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.succesMessage.observe(viewLifecycleOwner) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.email.observe(viewLifecycleOwner) {
            sharedPref.setData(it)
        }

    }

    private fun checkLogin() {
        if (sharedPref.isLogin()) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }

}