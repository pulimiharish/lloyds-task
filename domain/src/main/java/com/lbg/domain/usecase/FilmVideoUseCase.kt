package com.lbg.domain.usecase

import com.lbg.domain.repository.FilmsRepository
import javax.inject.Inject

class FilmVideoUseCase @Inject constructor(private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke(id: Int) = filmsRepository.getVideo(id)
}