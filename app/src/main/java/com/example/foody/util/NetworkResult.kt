package com.example.foody.util

//sealed class 란 enum class 의 확장 형태이다, sealed 클래스는 클래스들을 묶은 클래스이다.
sealed class    NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    // 네트워크 상태에 따라 class 를 호출한다.
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}