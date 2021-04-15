package com.example.foody.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// 컴파일시 컴포넌트 빌딩에 필요한 클래스를 초기화 시켜줌
// 왜? ApplicationClass 가 필요할까
// Hilt 라이브러리는 ApplicationClass 와  ApplicationContext 에 접근하여 많은 일들을 해야한다.
// 클래스 윗부분에 @HiltAndroidApp 어노테이션을 써줘야한다.
// Hilt Code 시작점 정의 (의존성 주입 시작점 지정)
// @AndroidEntryPoint 이라는 어노테이션도 있다
@HiltAndroidApp
class MyApplication : Application(){

}