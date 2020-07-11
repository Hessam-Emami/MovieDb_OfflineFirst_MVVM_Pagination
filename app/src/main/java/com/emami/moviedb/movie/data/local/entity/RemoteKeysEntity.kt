package com.emami.moviedb.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val repoId: Long,
    val nextKey: Int?
)