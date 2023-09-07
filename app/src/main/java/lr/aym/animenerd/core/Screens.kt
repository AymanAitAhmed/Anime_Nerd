package lr.aym.animenerd.core

sealed class Screens( val route:String){
    object SplashScreen : Screens("splash_screen")
    object MainMenuScreen : Screens("main_menu_screen")
    object QuestionScreen : Screens("question_screen")
}
