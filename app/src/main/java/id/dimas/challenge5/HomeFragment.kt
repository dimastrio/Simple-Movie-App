package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.dimas.challenge5.adapter.MovieAdapter
import id.dimas.challenge5.databinding.FragmentHomeBinding
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.service.TMDBApiService
import id.dimas.challenge5.service.TMDBClient
import id.dimas.challenge5.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val apiService: TMDBApiService by lazy { TMDBClient.instance }
    private val viewModel: HomeViewModel by viewModelsFactory { HomeViewModel(apiService) }

    private lateinit var movieAdapter: MovieAdapter

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
        observeData()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeData() {
        viewModel.dataError.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            movieAdapter.updateData(it.movieItems)
            Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}