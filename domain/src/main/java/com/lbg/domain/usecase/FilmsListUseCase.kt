package com.lbg.domain.usecase

import com.lbg.domain.model.Film
import com.lbg.domain.repository.FilmsRepository
import com.lbg.domain.shared.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FilmsListUseCase @Inject constructor(private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke(page: Int = 1): Flow<Result<List<Film>>> = filmsRepository.getFilmsList(page)
}