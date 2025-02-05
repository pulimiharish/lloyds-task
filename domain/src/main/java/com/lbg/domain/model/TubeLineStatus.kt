package com.lbg.domain.model

data class TubeLineStatus(
    val id: String? = null,
    val name: String? = null,
    val lineStatuses: List<LineStatus>? = null,
)