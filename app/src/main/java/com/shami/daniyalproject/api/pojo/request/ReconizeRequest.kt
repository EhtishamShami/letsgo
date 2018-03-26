package com.shami.daniyalproject.api.pojo.request

import java.io.File

/**
 * Created by Shami on 3/23/2018.
 */

data class ReconizeRequest(val isUserRecogn:String,
                           val fileName:String,
                           val file:File
                           )

