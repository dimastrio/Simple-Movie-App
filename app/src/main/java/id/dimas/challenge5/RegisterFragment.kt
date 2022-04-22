package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.dimas.challenge5.databinding.FragmentRegisterBinding
import id.dimas.challenge5.helper.UserRepo
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val viewModel: RegisterViewModel by viewModelsFactory { RegisterViewModel(userRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        addUser()
    }

    private fun addUser() {
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confPass = etConfPass.text.toString()
                if (username.isEmpty()) {
                    etUsername.error = "Username tidak boleh kosong"
                } else if (email.isEmpty()) {
                    etEmail.error = "Email tidak boleh kosong"
                } else if (password.isEmpty()) {
                    etPassword.error = "Password tidak boleh kosong"
                } else if (confPass.isEmpty()) {
                    etConfPass.error = "Konfirmasi Password harus diisi"
                } else {
                    if (password == confPass) {
                        viewModel.saveUserToDb(username, email, password)
                    } else {
                        etConfPass.error = "Konfirmasi Password Anda Tidak Sesuai"
                    }
                }
            }
        }
    }

    private fun observeData() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.successMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }


}