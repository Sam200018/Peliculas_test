package mx.ipn.escom.bautistas.peliculas.domain

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
import mx.ipn.escom.bautistas.peliculas.data.PeliculasDao
import mx.ipn.escom.bautistas.peliculas.data.PeliculasRepository
import mx.ipn.escom.bautistas.peliculas.model.MovieResponse
import java.io.IOException

sealed interface MovieDetailUiState {
    data class Success(val movie: MovieResponse) : MovieDetailUiState
    object Error : MovieDetailUiState
    object Loading : MovieDetailUiState
}


class MovieDetailsViewModel(
    private val peliculasRepository: PeliculasRepository,
    private val peliculasDao: PeliculasDao,
    private val idPelicula: String
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
                val result = peliculasRepository.getMovieDetail(idPelicula.toInt(), API_KEY)
                MovieDetailUiState.Success(result)
            } catch (e: IOException) {
                val localResponse = peliculasDao.getMoveById(idPelicula.toInt())
                val pelicula = MovieResponse(
                    adult = false,
                    budget = 10,
                    genres = listOf(),
                    id = localResponse.id.toLong(),
                    originalLanguage = "",
                    originalTitle = localResponse.originalTitle,
                    overview = localResponse.overview,
                    popularity = 20.0,
                    posterPath = localResponse.posterPath,
                    productionCompanies = listOf(),
                    productionCountries = listOf(),
                    releaseDate = "",
                    revenue = 1,
                    spokenLanguages = listOf(),
                    status = "",
                    title = localResponse.title,
                    video = false,
                    voteAverage = localResponse.voteAverage,
                    voteCount = 1
                )

                MovieDetailUiState.Success(pelicula)
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
                val peliculasDao = application.database.peliculasDao()
                MovieDetailsViewModel(peliculasRepository, peliculasDao, idPelicula = idPelicula)
            }
        }
    }
}