package com.emami.moviedb.movie.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emami.moviedb.movie.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query(
        "SELECT * FROM movie order by createdAt ASC"
    )
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun clearMovies()

    @Query("SELECT * FROM movie WHERE id LIKE :id")
    suspend fun getMovieById(id: Long): MovieEntity?
}