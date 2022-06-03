package com.example.wordledex.achievements

class AchievementsPresenter(private val view: AchievementsActivity, val model: AchievementsModel) {
    init {
        getAchievementList()
    }

    //Passes the evaluated achievement list to the RecyclerView
    private fun getAchievementList(){
        val achievementList = model.getAchievementList(view.playerData)
        view.displayAchievements(achievementList)
    }
}