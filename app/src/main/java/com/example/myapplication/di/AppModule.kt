package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.RaktaVahiniDatabase
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.repository.*
import com.example.myapplication.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RaktaVahiniDatabase {
        return Room.databaseBuilder(
            context,
            RaktaVahiniDatabase::class.java,
            "rakta_vahini_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUserDao(db: RaktaVahiniDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Provides
    @Singleton
    fun provideBloodRepository(): BloodRepository = BloodRepositoryImpl()

    @Provides
    @Singleton
    fun provideAdminRepository(): AdminRepository = AdminRepositoryImpl()

    @Provides
    @Singleton
    fun provideEmergencyRepository(): EmergencyRepository = EmergencyRepositoryImpl()
}
