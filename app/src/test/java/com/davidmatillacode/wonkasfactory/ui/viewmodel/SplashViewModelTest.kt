package com.davidmatillacode.wonkasfactory.ui.viewmodel

import com.davidmatillacode.wonkasfactory.ln.LoadAllStaffList
import com.davidmatillacode.wonkasfactory.ln.StoreStaffListDB
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SplashViewModelTest{

    @RelaxedMockK
    private lateinit var loadAllStaffList : LoadAllStaffList
    @RelaxedMockK
    private lateinit var storeStaffListDB : StoreStaffListDB

    private lateinit var viewModel: SplashViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        viewModel = SplashViewModel(loadAllStaffList,storeStaffListDB)
    }

    @Test
    fun `verify when API resturn empty list goes well`() = runBlocking {
        coEvery { loadAllStaffList.invoke() } returns emptyList()
        viewModel.loadAllData()
        coVerify (exactly = 1){ viewModel.loadEndEvent.postValue(true) }
    }
}