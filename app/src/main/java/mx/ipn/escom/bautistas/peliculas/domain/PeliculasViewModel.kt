package mx.ipn.escom.bautistas.peliculas.domain

import android.util.Log
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
import coil.network.HttpException
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.peliculas.PeliculasApplication
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
    private val peliculasRepository: PeliculasRepository
) : ViewModel() {
    private val topMoviesPage = mutableStateOf(1)
    private val onCinemaPage = mutableStateOf(1)
    private val _topMovies = mutableStateListOf<Pelicula>()
    private val _onCinema = mutableStateListOf<Pelicula>()

    private val API_KEY: String = "4a428a2deefcff39097ea4a9d69f62c8"
    var peliculasUiState: PeliculasUiState by mutableStateOf(PeliculasUiState.Loading)
        private set


    init {
        viewModelScope.launch {
            _topMovies.addAll(getTopMovies())
            _onCinema.addAll(getOnCinema())
            peliculasUiState =
                PeliculasUiState.Success(topMovies = _topMovies, onCinema = _onCinema)

        }
    }

    private suspend fun getTopMovies(): List<Pelicula> {
        return try {
            val response =
                peliculasRepository.getTopMoviesData(apikey = API_KEY, topMoviesPage.value)
            response.results
        } catch (e: IOException) {
            peliculasUiState = PeliculasUiState.Error
            listOf()
        } catch (e: HttpException) {
            peliculasUiState = PeliculasUiState.Error
            listOf()
        }
    }

    private suspend fun getOnCinema(): List<Pelicula> {
        return try {
            val result = peliculasRepository.getOnCinemaData(apikey = API_KEY, onCinemaPage.value)
            result.results
        } catch (e: IOException) {
            peliculasUiState = PeliculasUiState.Error
            listOf()
        } catch (e: HttpException) {
            peliculasUiState = PeliculasUiState.Error
            listOf()
        }
    }

    fun loadMoreTopMovies() {
        viewModelScope.launch {
            topMoviesPage.value = topMoviesPage.value + 1
            peliculasUiState = try {
                val response =
                    peliculasRepository.getTopMoviesData(apikey = API_KEY, topMoviesPage.value)
                _topMovies.addAll(response.results)
                PeliculasUiState.Success(_topMovies, _onCinema)
            } catch (e: IOException) {
                PeliculasUiState.Error
            } catch (e: HttpException) {
                PeliculasUiState.Error
            }
        }
    }

    fun loadMoreOnCinema() {
        Log.i("Load", "More data")
        viewModelScope.launch {
            onCinemaPage.value = onCinemaPage.value + 1
            peliculasUiState = try {
                val response =
                    peliculasRepository.getOnCinemaData(apikey = API_KEY, onCinemaPage.value)
                _onCinema.addAll(response.results)
                PeliculasUiState.Success(_topMovies, _onCinema)
            } catch (e: IOException) {
                PeliculasUiState.Error
            } catch (e: HttpException) {
                PeliculasUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val peliculasRepository = application.container.peliculasRepository
                PeliculasViewModel(peliculasRepository)
            }
        }
    }
}