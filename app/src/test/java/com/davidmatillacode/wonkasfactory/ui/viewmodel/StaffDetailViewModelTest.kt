package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.ln.GetWorkerDetailWithIdDB
import com.davidmatillacode.wonkasfactory.ln.LoadWorkerDetailWithId
import com.davidmatillacode.wonkasfactory.ln.UpdateWorkerDetailDB
import com.davidmatillacode.wonkasfactory.rules.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StaffDetailViewModelTest{

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getWorkerDetailDB: GetWorkerDetailWithIdDB

    @Mock
    private lateinit var loadWorkerDetail: LoadWorkerDetailWithId

    @Mock
    private lateinit var updateWorkerDetailDB: UpdateWorkerDetailDB

    @Mock
    private lateinit var viewModel : StaffDetailViewModel

    @Mock
    private lateinit var workerObserver: Observer<StaffWorker>

    @Before
    fun onBefore() {
        viewModel = StaffDetailViewModel(getWorkerDetailDB, loadWorkerDetail, updateWorkerDetailDB)
    }

    @Test
    fun `check data is not posted when the DB and API doesnt return some data`(){
        testCoroutineRule.runBlockingTest {
            val id = 0
            Mockito.doReturn(null).`when`(getWorkerDetailDB)
                .invoke(0)
            Mockito.doReturn(null).`when`(loadWorkerDetail)
                .invoke(0)
            viewModel.workerDetailData.observeForever(workerObserver)
            viewModel.loadWorkerInfo(id)

            Mockito.verify(workerObserver,Mockito.never()).onChanged(null)
            Mockito.verify(updateWorkerDetailDB,Mockito.never())
                .invoke(StaffWorker(0,"","",null,"","","","",0,"",0))

            viewModel.workerDetailData.removeObserver(workerObserver)

        }
    }

    @Test
    fun `check data is not posted when the DB doesnt return some data but API yes`(){
        testCoroutineRule.runBlockingTest {
            val id = 0
            val w = StaffWorker(0,"","",null,"","","","",0,"",0)
            Mockito.doReturn(null).`when`(getWorkerDetailDB)
                .invoke(0)
            Mockito.doReturn(w).`when`(loadWorkerDetail)
                .invoke(0)
            viewModel.workerDetailData.observeForever(workerObserver)
            viewModel.loadWorkerInfo(id)

            Mockito.verify(workerObserver,Mockito.times(1)).onChanged(w)
            Mockito.verify(updateWorkerDetailDB)
                .invoke(w)

            viewModel.workerDetailData.removeObserver(workerObserver)

        }
    }

    @Test
    fun `check data is posted when the DB and API does return some data`(){
        testCoroutineRule.runBlockingTest {
            val id = 0
            val w = StaffWorker(0,"","",null,"","","","",0,"",0)
            Mockito.doReturn(w).`when`(getWorkerDetailDB)
                .invoke(0)
            Mockito.doReturn(w).`when`(loadWorkerDetail)
                .invoke(0)
            viewModel.workerDetailData.observeForever(workerObserver)
            viewModel.loadWorkerInfo(id)

            Mockito.verify(workerObserver,Mockito.times(2)).onChanged(w)
            Mockito.verify(updateWorkerDetailDB)
                .invoke(w)

            viewModel.workerDetailData.removeObserver(workerObserver)

        }
    }

    @Test
    fun `check data is posted when the DB does return some data but API doesnt`(){
        testCoroutineRule.runBlockingTest {
            val id = 0
            val w = StaffWorker(0,"","",null,"","","","",0,"",0)
            Mockito.doReturn(w).`when`(getWorkerDetailDB)
                .invoke(0)
            Mockito.doReturn(null).`when`(loadWorkerDetail)
                .invoke(0)
            viewModel.workerDetailData.observeForever(workerObserver)
            viewModel.loadWorkerInfo(id)

            Mockito.verify(workerObserver,Mockito.times(1)).onChanged(w)
            Mockito.verify(updateWorkerDetailDB,Mockito.never())
                .invoke(w)

            viewModel.workerDetailData.removeObserver(workerObserver)

        }
    }
}