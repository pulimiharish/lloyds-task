package com.lbg.data.mapper

interface DataMapper<T, R> {
    fun map(data: T): R
}