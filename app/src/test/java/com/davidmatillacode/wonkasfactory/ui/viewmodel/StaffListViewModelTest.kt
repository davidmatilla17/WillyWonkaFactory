package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.ln.GetProfessionsDB
import com.davidmatillacode.wonkasfactory.ln.GetStaffListDB
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
class StaffListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getStaffListDB: GetStaffListDB

    @Mock
    private lateinit var getProfessionsDB: GetProfessionsDB

    @Mock
    private lateinit var staffListObserver: Observer<List<StaffWorker>>

    @Mock
    private lateinit var genderFilterObserver: Observer<String>

    @Mock
    private lateinit var professionFilterObserver: Observer<String>

    @Mock
    private lateinit var professionsListFilterObserver: Observer<List<String?>>

    private lateinit var viewModel: StaffListViewModel

    @Before
    fun onBefore() {
        viewModel = StaffListViewModel(getStaffListDB, getProfessionsDB)
    }


    @Test
    fun `test get staff empty list when empty filters are sent and post value`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = ""
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)
            viewModel.staffListData.observeForever(staffListObserver)
            viewModel.getStaffList(genderFilter, professionFilter)
            verify(staffListObserver).onChanged(emptyList())
            viewModel.staffListData.removeObserver(staffListObserver)
        }
    }

    @Test
    fun `test when staff list is requested filters are correctly set`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = "prueba1"
            val professionFilter = "prueba 2"
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)

            viewModel.getStaffList(genderFilter, professionFilter)
            verify(genderFilterObserver).onChanged(genderFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `test when staff list is requested and there is no filter passed verify are set as empty string`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = ""
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)

            viewModel.getStaffList()
            verify(genderFilterObserver).onChanged(genderFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `test when staff list is reload with empty filters, are correcty set again`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = ""
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
            viewModel.genderFilterData.value = genderFilter
            viewModel.professionFilterData.value = professionFilter

            viewModel.reloadStaffList()

            verify(genderFilterObserver).onChanged(genderFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `test when staff list is reload with filters, are correcty set again`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = "prueba 1"
            val professionFilter = "prueba 2"
            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
            viewModel.genderFilterData.value = genderFilter
            viewModel.professionFilterData.value = professionFilter

            viewModel.reloadStaffList()

            verify(genderFilterObserver).onChanged(genderFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `apply a gender filter and is correctly post`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = "prueba"
            val professionFilter = ""

            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)

            viewModel.applyGenderFilter(genderFilter)
            verify(getStaffListDB).invoke(genderFilter, professionFilter)
            verify(genderFilterObserver).onChanged(genderFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
        }
    }

    @Test
    fun `apply a empty gender filter and is correctly post`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = ""

            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)

            viewModel.applyGenderFilter(genderFilter)
            verify(getStaffListDB).invoke(genderFilter, professionFilter)
            verify(genderFilterObserver).onChanged(genderFilter)

            viewModel.genderFilterData.observeForever(genderFilterObserver)
        }
    }


    @Test
    fun `apply a profession filter and is correctly post`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = "prueba"

            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.professionFilterData.observeForever(professionFilterObserver)

            viewModel.applyProfesionFilter(professionFilter)
            verify(getStaffListDB).invoke(genderFilter, professionFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `apply a empty profession filter and check is correctly post`() {
        testCoroutineRule.runBlockingTest {
            val genderFilter = ""
            val professionFilter = ""

            Mockito.doReturn(emptyList<StaffWorker>()).`when`(getStaffListDB)
                .invoke(genderFilter, professionFilter)

            viewModel.professionFilterData.observeForever(professionFilterObserver)

            viewModel.applyProfesionFilter(professionFilter)
            verify(getStaffListDB).invoke(genderFilter, professionFilter)
            verify(professionFilterObserver).onChanged(professionFilter)

            viewModel.professionFilterData.observeForever(professionFilterObserver)
        }
    }

    @Test
    fun `get professions with filter and is correctly post`() {
        testCoroutineRule.runBlockingTest {
            val professionFilter = ""

            Mockito.doReturn(emptyList<String?>()).`when`(getProfessionsDB)
                .invoke(professionFilter)

            viewModel.professionListFilterData.observeForever(professionsListFilterObserver)

            viewModel.seachProfession(professionFilter)
            verify(getProfessionsDB).invoke(professionFilter)
            verify(professionsListFilterObserver).onChanged(emptyList<String?>())

            viewModel.professionListFilterData.observeForever(professionsListFilterObserver)
        }
    }


}