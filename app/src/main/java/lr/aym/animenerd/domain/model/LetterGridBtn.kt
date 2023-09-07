package lr.aym.animenerd.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LetterGridBtn(
    val letter : Char,
    val isClicked : MutableState<Boolean> = mutableStateOf(false),
    val isRevealed : MutableState<Boolean> = mutableStateOf(false),
    val index : Int
)
