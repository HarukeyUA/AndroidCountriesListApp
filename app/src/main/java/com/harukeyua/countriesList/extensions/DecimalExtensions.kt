package com.harukeyua.countriesList.extensions

import android.content.res.Resources

val Int.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()