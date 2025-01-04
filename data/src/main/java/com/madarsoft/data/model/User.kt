package com.madarsoft.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.madarsoft.core.domain.model.Gender


@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo("id"          ) @SerializedName("id"            ) var id        : Int?     = 1,
    @ColumnInfo("name"        ) @SerializedName("name"          ) var name      : String? = null,
    @ColumnInfo("age"         ) @SerializedName("age"           ) var age       : Int?    = null,
    @ColumnInfo("jobTitle"    ) @SerializedName("jobTitle"      ) var jobTitle  : String? = null,
    @ColumnInfo("gender"      ) @SerializedName("gender"        ) var gender    : String? = null,

){
    companion object {
        infix fun from(user: com.madarsoft.core.domain.model.User): User {
            return User(
                id = 1,
                name = user.name,
                age = user.age,
                jobTitle = user.jobTitle,
                gender = user.gender?.value
            )
        }

        infix fun to(user: User): com.madarsoft.core.domain.model.User {
            return com.madarsoft.core.domain.model.User(
                id = 1,
                name = user.name,
                age = user.age,
                jobTitle = user.jobTitle,
                gender = Gender.from(user.gender)
            )
        }

    }
}