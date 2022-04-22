package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import id.dimas.challenge5.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
        moveToRegist(view)
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

}