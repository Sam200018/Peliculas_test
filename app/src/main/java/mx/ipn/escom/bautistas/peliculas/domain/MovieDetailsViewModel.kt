package mx.ipn.escom.bautistas.peliculas.domain

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.peliculas.PeliculasApplication
import mx.ipn.escom.bautistas.peliculas.data.PeliculasRepository
import mx.ipn.escom.bautistas.peliculas.model.MovieResponse
import java.io.IOException

sealed interface MovieDetailUiState {
    data class Success(val movie: MovieResponse) : MovieDetailUiState
    object Error : MovieDetailUiState
    object Loading : MovieDetailUiState
}


class MovieDetailsViewModel(
    private val peliculasRepository: PeliculasRepository, private val idPelicula: String
) : ViewModel() {
    private val API_KEY: String = "4a428a2deefcff39097ea4a9d69f62c8"
    var movieDetailUiState: MovieDetailUiState by mutableStateOf(MovieDetailUiState.Loading)
        private set


    init {
        getMovieDetail()
    }

    private fun getMovieDetail() {
        viewModelScope.launch {
            movieDetailUiState = try {
                Log.i("movie_id", idPelicula)
                val result = peliculasRepository.getMovieDetail(idPelicula.toInt(), API_KEY)
                MovieDetailUiState.Success(result)
            } catch (e: IOException) {
                MovieDetailUiState.Error
            } catch (e: HttpException) {
                MovieDetailUiState.Error

            }
        }
    }

    companion object {
        fun createFactory(idPelicula: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val peliculasRepository = application.container.peliculasRepository
                MovieDetailsViewModel(peliculasRepository, idPelicula = idPelicula)
            }
        }
    }
}