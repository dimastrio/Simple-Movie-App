package id.dimas.challenge5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.dimas.challenge5.databinding.FragmentDetailBinding
import id.dimas.challenge5.helper.viewModelsFactory
import id.dimas.challenge5.service.TMDBApiService
import id.dimas.challenge5.service.TMDBClient
import id.dimas.challenge5.viewmodel.DetailMovieViewModel
import id.dimas.challenge5.viewmodel.HomeViewModel.Companion.KEY_MOVIE_ID

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val apiService: TMDBApiService by lazy { TMDBClient.instance }
    private val viewModel: DetailMovieViewModel by viewModelsFactory {
        DetailMovieViewModel(
            apiService
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieId()
        observeData()
    }

    private fun getMovieId() {
        val movieId = arguments?.getInt(KEY_MOVIE_ID)
        viewModel.getDetailMovie(movieId ?: 0)
    }

    private fun observeData() {
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.apply {
                tvTitleMovie.text = it.title
                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                    .into(ivMovie)
                tvOverview.text = it.overview
            }
        }

        viewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }


}