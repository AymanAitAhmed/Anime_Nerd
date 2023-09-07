package lr.aym.animenerd.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import lr.aym.animenerd.domain.model.LetterGridBtn
import kotlin.random.Random

val Int.nonSP
@Composable
get() = (this / LocalDensity.current.fontScale).sp


fun generateScrambledArray(answer: String): List<LetterGridBtn> {
    val charArray = CharArray(14)
    val letterGridCharsList = mutableListOf<LetterGridBtn>()

    // Fill the array with the answer letters
    for (i in answer.indices) {
        charArray[i] = answer[i]
    }

    // Generate random letters for the remaining positions
    for (i in answer.length until 14) {
        charArray[i] = generateRandomLetter()
    }

    // Scramble the character array
    charArray.shuffle()
    charArray.forEachIndexed { index, c ->
        letterGridCharsList.add(LetterGridBtn(
            letter = c,
            index = index
        ))
    }
    return letterGridCharsList.toList()
}

private fun generateRandomLetter(): Char {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val randomIndex = Random.nextInt(alphabet.length)
    return alphabet[randomIndex]
}

fun MutableList<LetterGridBtn?>.addElementToFirstEmptyIndex(element: LetterGridBtn) {
    val emptyIndex = indexOfFirst { it == null }

    if (emptyIndex != -1) {
        this[emptyIndex] = element
    }
}

fun <T> List<T>.sizeWithoutNulls():Int{
    var itr = 0
    this.forEach {
        if (it != null)
            itr++
    }
    return itr
}