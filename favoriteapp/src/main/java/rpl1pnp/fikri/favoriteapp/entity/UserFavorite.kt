

package rpl1pnp.fikri.favoriteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userFavorite")
data class UserFavorite(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "followers") val followers: Int?,
    @ColumnInfo(name = "following") val following: Int?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "company") val company: String?,
    @ColumnInfo(name = "repositories") val repositories: Int?
)
