package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import id.dimas.challenge5.database.User
import id.dimas.challenge5.databinding.FragmentProfileBinding
import id.dimas.challenge5.helper.SharedPref
import id.dimas.challenge5.helper.SharedPref.Companion.KEY_EMAIL
import id.dimas.challenge5.helper.SharedPref.Companion.KEY_PASS
import id.dimas.challenge5.helper.UserRepo
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.service.TMDBApiService
import id.dimas.challenge5.service.TMDBClient
import id.dimas.challenge5.viewmodel.ProfileViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val sharedPref: SharedPref by lazy { SharedPref(requireContext()) }

    private val apiService: TMDBApiService by lazy { TMDBClient.instance }
    private val viewModel: ProfileViewModel by viewModelsFactory {
        ProfileViewModel(
            userRepo,
            sharedPref
        )
    }

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
        observeData()
        setProfile()
        viewModel.getUser()
    }

    private fun logout() {
        binding.apply {
            btnLogout.setOnClickListener {
                sharedPref.clearPref()
                it.findNavController()
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
        }
    }


    private fun setProfile() {
        binding.apply {
            btnUpdate.setOnClickListener {
                val username = etUsername.text.toString()
                val fullname = etFullname.text.toString()
                val datebirth = etDatebirth.text.toString()
                val address = etAddress.text.toString()
                when {
                    username.isEmpty() -> {
                        etUsername.error = "Username tidak Boleh Kosong"
                    }
                    fullname.isEmpty() -> {
                        etFullname.error = "Nama lengkap tidak boleh kosong"
                    }
                    datebirth.isEmpty() -> {
                        etDatebirth.error = "Tanggal lahir tidak boleh kosong"
                    }
                    address.isEmpty() -> {
                        etAddress.error = "Alamat tidak boleh kosong"
                    }
                    else -> {
                        val newUser = User(
                            sharedPref.getUserId(),
                            username,
                            sharedPref.getEmail(KEY_EMAIL),
                            sharedPref.getPassword(KEY_PASS),
                            fullname,
                            datebirth,
                            address
                        )
                        viewModel.updateToDb(newUser)
                    }
                }
            }

        }
    }

    private fun observeData() {
        viewModel.updateMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                etUsername.setText(it.username)
                etFullname.setText(it.fullname)
                etDatebirth.setText(it.datebirth)
                etAddress.setText(it.address)
            }
        }

    }


}