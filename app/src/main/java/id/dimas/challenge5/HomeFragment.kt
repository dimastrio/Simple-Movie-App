package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.dimas.challenge5.adapter.MovieAdapter
import id.dimas.challenge5.databinding.FragmentHomeBinding
import id.dimas.challenge5.helper.SharedPref
import id.dimas.challenge5.helper.UserRepo
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.service.TMDBApiService
import id.dimas.challenge5.service.TMDBClient
import id.dimas.challenge5.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val apiService: TMDBApiService by lazy { TMDBClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val viewModel: HomeViewModel by viewModelsFactory {
        HomeViewModel(
            apiService,
            sharedPref
        )
    }

    private lateinit var movieAdapter: MovieAdapter

    private val sharedPref: SharedPref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getAllMovies()
        viewModel.getUsername()
        observeData()
        moveProfile()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter {
            viewModel.action(it)
        }
        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    private fun moveProfile() {
        binding.apply {
            ivProfile.setOnClickListener {
                it.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
            }
        }
    }

    private fun observeData() {
        viewModel.dataError.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            movieAdapter.updateData(it.movieItems)
        }

        viewModel.movieBundle.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, it)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.tvUsername.text = it
        }
    }
}