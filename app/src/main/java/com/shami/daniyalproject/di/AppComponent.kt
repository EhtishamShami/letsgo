package com.shami.daniyalproject.di

import com.shami.daniyalproject.DaniyalApplication
import com.shami.daniyalproject.activities.loginregister.LoginViewModel
import com.shami.daniyalproject.activities.loginregister.RegisterViewModel
import com.shami.daniyalproject.fragments.ReconizeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Shami on 3/23/2018.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class,ApiModule::class))
interface AppComponent
{

    fun inject(daniyalApplication: DaniyalApplication)

    fun inject(loginViewModel: LoginViewModel)

    fun inject(registerViewModel: RegisterViewModel)


    fun inject(reconizeFragmentViewModel: ReconizeFragmentViewModel)

    fun inject(registerViewModel: com.shami.daniyalproject.fragments.RegisterViewModel)





}