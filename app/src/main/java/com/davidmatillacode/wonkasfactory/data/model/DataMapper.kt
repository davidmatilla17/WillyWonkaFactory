package com.davidmatillacode.wonkasfactory.data.model

import com.davidmatillacode.wonkasfactory.data.model.entities.Favorite
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.model.entities.db.FavoriteRlm
import com.davidmatillacode.wonkasfactory.data.model.entities.db.StaffWorkerRlm

class DataMapper {
    companion object {

        fun staffListEntityToRlm(workersList: List<StaffWorker>): List<StaffWorkerRlm> {
            return workersList.map { staffWorker -> staffEntityToRlm(staffWorker) }
        }

        fun staffEntityToRlm(worker: StaffWorker): StaffWorkerRlm {
            val staffRlm = StaffWorkerRlm()
            staffRlm.id = worker.id
            staffRlm.firstName = worker.firstName
            staffRlm.lastName = worker.lastName
            staffRlm.favorite = favoriteEntityToRlm(worker.favorite)
            staffRlm.gender = worker.gender
            staffRlm.image = worker.image
            staffRlm.profession = worker.profession
            staffRlm.email = worker.email
            staffRlm.age = worker.age
            staffRlm.country = worker.country
            staffRlm.height = worker.height
            return staffRlm
        }

        fun favoriteEntityToRlm(fav: Favorite?): FavoriteRlm {
            val favorite = FavoriteRlm()
            favorite.color = fav?.color
            favorite.song = fav?.song
            favorite.food = fav?.food
            favorite.randomString = fav?.randomString
            return favorite
        }


        fun staffListRlmToEntity(workersList: List<StaffWorkerRlm>): List<StaffWorker> {
            return workersList.map { staffWorker -> staffRlmToEntity(staffWorker) }
        }

        fun staffRlmToEntity(worker: StaffWorkerRlm): StaffWorker {

            return StaffWorker(
                worker.id,
                worker.firstName,
                worker.lastName,
                favoriteRlmToEntity(worker.favorite),
                worker.gender,
                worker.image,
                worker.profession,
                worker.email,
                worker.age,
                worker.country,
                worker.height
            )
        }

        fun favoriteRlmToEntity(fav: FavoriteRlm?): Favorite {
            return Favorite(fav?.color, fav?.food, fav?.randomString, fav?.song)
        }

    }
}