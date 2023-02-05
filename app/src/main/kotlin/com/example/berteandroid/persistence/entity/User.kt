package com.example.berteandroid.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(@PrimaryKey val id: UUID, @ColumnInfo(name = "name") val name : String)
