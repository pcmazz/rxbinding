package com.falcotech.mazz.rxbindinglibrary.core

data class BT_ServerResponse(var status: String = "",
                             var payload: Any?,
                             var isSuccess: Boolean = true)