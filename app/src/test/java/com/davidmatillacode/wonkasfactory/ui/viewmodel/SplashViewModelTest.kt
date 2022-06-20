package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.Resource
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.ln.LoadAllStaffList
import com.davidmatillacode.wonkasfactory.ln.StoreStaffListDB
import com.davidmatillacode.wonkasfactory.rules.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var loadAllStaffList: LoadAllStaffList

    @Mock
    private lateinit var storeStaffListDB: StoreStaffListDB

    private lateinit var viewModel: SplashViewModel

    @Before
    fun onBefore() {
        viewModel = SplashViewModel(loadAllStaffList, storeStaffListDB)
    }

    @Test
    fun `verify when API resturn empty list goes well`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(loadAllStaffList).invoke()
            viewModel.loadAllData()
            verify(loadAllStaffList).invoke()
            verify(storeStaffListDB).invoke(emptyList<StaffWorker>())
        }
    }
}