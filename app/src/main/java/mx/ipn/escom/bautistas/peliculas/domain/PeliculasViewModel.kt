package mx.ipn.escom.bautistas.peliculas.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.peliculas.PeliculasApplication
import mx.ipn.escom.bautistas.peliculas.data.PeliculasDao
import mx.ipn.escom.bautistas.peliculas.data.PeliculasLocal
import mx.ipn.escom.bautistas.peliculas.data.PeliculasRepository
import mx.ipn.escom.bautistas.peliculas.model.Pelicula
import java.io.IOException

sealed interface PeliculasUiState {

    data class Success(val topMovies: List<Pelicula>, val onCinema: List<Pelicula>) :
        PeliculasUiState

    object Error : PeliculasUiState
    object Loading : PeliculasUiState
}


class PeliculasViewModel(
    private val peliculasRepository: PeliculasRepository,
    private val peliculasDao: PeliculasDao
) : ViewModel() {
    private val topMoviesPage = mutableStateOf(1)
    private val onCinemaPage = mutableStateOf(1)
    private val _topMovies = mutableStateListOf<Pelicula>()
    private val _onCinema = mutableStateListOf<Pelicula>()

    var searchInput: String by mutableStateOf("")
        private set

    private val API_KEY: String = "4a428a2deefcff39097ea4a9d69f62c8"
    var peliculasUiState: PeliculasUiState by mutableStateOf(PeliculasUiState.Loading)
        private set


    init {
        loadMovies()
    }

    private suspend fun getTopMovies(): List<Pelicula> {
        return try {
            val response =
                peliculasRepository.getTopMoviesData(apikey = API_KEY, topMoviesPage.value)
            response.results.forEach { pelicula ->
                val peliculaLocal = PeliculasLocal(
                    pelicula.backdropPath,
                    pelicula.id,
                    pelicula.originalTitle,
                    pelicula.overview,
                    pelicula.posterPath,
                    pelicula.title,
                    pelicula.voteAverage,
                    "topMovies"
                )
                peliculasDao.insert(peliculaLocal)
            }
            response.results

        } catch (e: IOException) {
            val localResponse = peliculasDao.getTopMovies()
            localResponse.map { peliculasLocal ->
                Pelicula(
                    adult = false,
                    peliculasLocal.backdropPath,
                    genreIDS = listOf(),
                    peliculasLocal.id,
                    originalLanguage = "",
                    peliculasLocal.originalTitle,
                    peliculasLocal.overview,
                    popularity = 20.0,
                    peliculasLocal.posterPath,
                    releaseDate = "",
                    peliculasLocal.title,
                    video = false,
                    peliculasLocal.voteAverage,
                    voteCount = 1
                )
            }
        }
//        catch (e: HttpException) {
//            peliculasUiState = PeliculasUiState.Error
//            listOf()
//        }
    }

    private suspend fun getOnCinema(): List<Pelicula> {
        return try {
            val response = peliculasRepository.getOnCinemaData(apikey = API_KEY, onCinemaPage.value)
            response.results.forEach { pelicula ->
                val peliculaLocal = PeliculasLocal(
                    pelicula.backdropPath,
                    pelicula.id,
                    pelicula.originalTitle,
                    pelicula.overview,
                    pelicula.posterPath,
                    pelicula.title,
                    pelicula.voteAverage,
                    "onCinema"
                )
                peliculasDao.insert(peliculaLocal)
            }
            response.results
        } catch (e: IOException) {
            val localResponse = peliculasDao.getOnCinema()
            localResponse.map { peliculasLocal ->
                Pelicula(
                    adult = false,
                    peliculasLocal.backdropPath,
                    genreIDS = listOf(),
                    peliculasLocal.id,
                    originalLanguage = "",
                    peliculasLocal.originalTitle,
                    peliculasLocal.overview,
                    popularity = 20.0,
                    peliculasLocal.posterPath,
                    releaseDate = "",
                    peliculasLocal.title,
                    video = false,
                    peliculasLocal.voteAverage,
                    voteCount = 1
                )
            }
        }
    }

    fun filterMoviesByString(filter: String) {
        searchInput = filter
        val topMoviesFilter = _topMovies.filter { pelicula ->
            pelicula.title.contains(filter)
        }
        val onCinemaFilter = _onCinema.filter { pelicula ->
            pelicula.title.contains(filter)
        }
        peliculasUiState = PeliculasUiState.Success(topMoviesFilter, onCinemaFilter)
    }

    fun loadMovies(){
        viewModelScope.launch {
            _topMovies.addAll(getTopMovies())
            _onCinema.addAll(getOnCinema())
            peliculasUiState =
                PeliculasUiState.Success(topMovies = _topMovies, onCinema = _onCinema)
        }
    }


    fun loadMoreTopMovies() {
        viewModelScope.launch {
            topMoviesPage.value = topMoviesPage.value + 1
            try {
                val response =
                    peliculasRepository.getTopMoviesData(apikey = API_KEY, topMoviesPage.value)
                _topMovies.addAll(response.results)
                peliculasUiState =
                    PeliculasUiState.Success(_topMovies, _onCinema)
            } catch (e: IOException) {

            }
        }
    }

    fun loadMoreOnCinema() {
        viewModelScope.launch {
            onCinemaPage.value = onCinemaPage.value + 1
            try {
                val response =
                    peliculasRepository.getOnCinemaData(apikey = API_KEY, onCinemaPage.value)
                _onCinema.addAll(response.results)
                peliculasUiState =
                    PeliculasUiState.Success(_topMovies, _onCinema)
            } catch (e: IOException) {

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val peliculasRepository = application.container.peliculasRepository
                val peliculasDao = application.database.peliculasDao()
                PeliculasViewModel(peliculasRepository, peliculasDao)
            }
        }
    }
}