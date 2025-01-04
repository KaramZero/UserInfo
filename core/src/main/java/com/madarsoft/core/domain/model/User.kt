package com.madarsoft.core.domain.model

import com.google.gson.annotations.SerializedName


data class User (

  @SerializedName("id"         ) var id         : Int?    = null,
  @SerializedName("name"       ) var name       : String? = null,
  @SerializedName("age"        ) var age        : Int?    = null,
  @SerializedName("jobTitle"   ) var jobTitle   : String? = null,
  @SerializedName("gender"     ) var gender     : Gender? = null,

)