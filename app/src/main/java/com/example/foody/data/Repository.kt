package com.example.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


// ActivityRetainedScoped

/***
 * ApplicationComponent의 하위 컴포넌트느로써 Activity의 생명주기를 lifetiome으로 갖는다.
 * Activity가 생성되는 시점에 생성되고, Activity가 파괴데는 시점에 파괴된다.
 */
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local=localDataSource
}