package lr.aym.animenerd.presentation.views.questionScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import lr.aym.animenerd.core.addElementToFirstEmptyIndex
import lr.aym.animenerd.core.generateScrambledArray
import lr.aym.animenerd.core.sizeWithoutNulls
import lr.aym.animenerd.data.database.Level
import lr.aym.animenerd.domain.model.LetterGridBtn
import lr.aym.animenerd.domain.repository.datastore.CurrentLevelRepository
import lr.aym.animenerd.domain.repository.LevelDbRepository
import lr.aym.animenerd.domain.repository.datastore.GameProgressStatusRepository
import lr.aym.animenerd.domain.repository.datastore.UserPointsRepository
import javax.inject.Inject

@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val userPointsRepository: UserPointsRepository,
    private val levelDbRepository: LevelDbRepository,
    private val currentLevelRepository: CurrentLevelRepository,
    private val gameProgressStatusRepo: GameProgressStatusRepository
) : ViewModel() {

    private val _userPoints: MutableStateFlow<Int> = MutableStateFlow(0)
    val userPoints = _userPoints.asStateFlow()

    private val _totalCountLevels = MutableStateFlow(1)
    val totalCountLevels = _totalCountLevels.asStateFlow()

    private val _lettersGridList: MutableStateFlow<List<LetterGridBtn>> =
        MutableStateFlow(emptyList())
    val lettersGridList = _lettersGridList.asStateFlow()

    private val _currentLevel = MutableStateFlow(Level(0, correctAnswer = ""))
    val currentLevel = _currentLevel.asStateFlow()

    private val _userAnswerInput: MutableStateFlow<List<LetterGridBtn?>> = MutableStateFlow(
        List(_currentLevel.value.correctAnswer.length) { null }
    )
    val userAnswerInput = _userAnswerInput.asStateFlow()

    private val _currentLevelProgress = MutableStateFlow(0)
    val currentLevelProgress = _currentLevelProgress.asStateFlow()

    private val _userAnswerCorrectness = MutableStateFlow(false)
    val userAnswerCorrectness = _userAnswerCorrectness.asStateFlow()

    private val _gameIsCompleted = MutableStateFlow(false)
    val gameIsCompleted = _gameIsCompleted.asStateFlow()

    private val _showRevealLetterDialog = MutableStateFlow(false)
    val showRevealLetterDialog = _showRevealLetterDialog.asStateFlow()

    private val _showEarnPointsDialog = MutableStateFlow(false)
    val showEarnPointsDialog = _showEarnPointsDialog.asStateFlow()

    private val _watchRewardedAdClicked = MutableStateFlow(false)
    val watchRewardedAdClicked = _watchRewardedAdClicked.asStateFlow()

    init {
        getUserPoints()
    }

    private fun generateLettres() {
        Log.d("myviewmodel", _currentLevel.value.correctAnswer)
        _lettersGridList.value =
            generateScrambledArray(_currentLevel.value.correctAnswer.uppercase())
    }

    private fun getUserPoints() {
        viewModelScope.launch {
            userPointsRepository.getUserPoints().collect {
                _userPoints.value = it
            }
        }
    }

    fun incrementUserPoints(count: Int) {
        viewModelScope.launch {
            userPointsRepository.incrementUserPointBy(count)
        }
    }

    fun decrementUserPoints(count: Int) {
        viewModelScope.launch {
            userPointsRepository.decrementUserPointBy(count)
        }
    }

    fun onLetterGridBtnClick(index: Int) {
        if (_userAnswerInput.value.count { it != null } < _currentLevel.value.correctAnswer.length) {
            val letterBtnClicked = _lettersGridList.value[index]
            letterBtnClicked.isClicked.value = true
            val list: MutableList<LetterGridBtn?> = _userAnswerInput.value.toMutableList()
            list.addElementToFirstEmptyIndex(letterBtnClicked)
            _userAnswerInput.value = list.toList()
            checkUserInputCorrectness()
        }
    }

    fun onAnswerFieldBtnClick(index: Int?) {
        index?.let {
            val letterBtnClicked = _lettersGridList.value[index]
            letterBtnClicked.isClicked.value = false
            val list: MutableList<LetterGridBtn?> = _userAnswerInput.value.toMutableList()
            list[list.indexOf(letterBtnClicked)] = null
            _userAnswerInput.value = list.toList()
        }
    }

    private fun checkUserInputCorrectness() {
        var userAnswer = ""
        _userAnswerInput.value.forEach { lettersGridbtn ->
            lettersGridbtn?.let {
                userAnswer += it.letter.toString()
            }
        }
        _userAnswerCorrectness.value =
            userAnswer.lowercase() == _currentLevel.value.correctAnswer.lowercase()
    }

    suspend fun onNextBtnClicked() {
        if ((_currentLevelProgress.value + 1) <= _totalCountLevels.value) {
            getCorrespondingLevelFromDb(_currentLevelProgress.value + 1)
            incrementUserPoints(1)
            _userAnswerCorrectness.value = false
        } else {
            gameProgressStatusRepo.saveGameProgressStatus(true)
        }
    }

    suspend fun getCorrespondingLevelFromDb(currentLevel: Int) {
        _currentLevel.value = levelDbRepository.getLeveLByNum(
            currentLevel
        ) ?: Level(
            0,
            correctAnswer = ""
        )
        _userAnswerInput.value = List(_currentLevel.value.correctAnswer.length) { null }
        generateLettres()

    }

    fun getCurrentLevelDatastore() {
        viewModelScope.launch {
            currentLevelRepository.getCurrentLevel().collect {
                _currentLevelProgress.value = it
                Log.d("currentlevel", "getCurrentLevelDatastore: $it")
            }
        }
    }

    fun onNewLevelLoaded() {
        viewModelScope.launch {
            Log.d("newLevel", _currentLevel.value.lvlNum.toString())
            currentLevelRepository.saveCurrentLevel(_currentLevel.value.lvlNum)
            Log.d("newLevel", _currentLevelProgress.value.toString())

        }
    }

    fun getTotalCountLevels() {
        viewModelScope.launch {
            _totalCountLevels.value = levelDbRepository.getTotalCountLevels()
        }
    }

    fun getGameProgressStatus() {
        viewModelScope.launch {
            gameProgressStatusRepo.getGameProgressStatus().collect {
                _gameIsCompleted.value = it
                Log.d("gameProgress", "getGameProgressStatus: $it")
            }
        }
    }

    suspend fun onRestartClick() {
        gameProgressStatusRepo.saveGameProgressStatus(false)
        currentLevelRepository.saveCurrentLevel(1)
        //Log.d("currentlevel", "onRestart: ${_currentLevelProgress.value}")
        getCorrespondingLevelFromDb(_currentLevelProgress.value)

    }

    fun onRevealLetterClick() {
        Log.d(
            "RevealLetter",
            "${_userAnswerInput.value.sizeWithoutNulls()}   ${_currentLevel.value.correctAnswer.length}"
        )
        if (_userPoints.value <= 0) {
            _showRevealLetterDialog.value = true
        } else if (_userAnswerInput.value.sizeWithoutNulls() < _currentLevel.value.correctAnswer.length) {
            val list: MutableList<LetterGridBtn?> = _userAnswerInput.value.toMutableList()
            val letter = _currentLevel.value.correctAnswer[list.indexOfFirst { it == null }]
            val letterGridBtn =
                _lettersGridList.value.find { it.letter.uppercase() == letter.uppercase() }
            letterGridBtn?.index?.let { index ->
                onLetterGridBtnClick(index)
                _userAnswerInput.value.find { it?.index == index }?.isRevealed?.value = true
                decrementUserPoints(1)
            }
        }
    }

    fun onDismissRevealLetterDialog() {
        _showRevealLetterDialog.value = false
    }

    fun onEarnPointsClick(){
        _showEarnPointsDialog.value = true
    }
    fun onDismissEarnPointsDialogClick(){
        _showEarnPointsDialog.value = false
    }
    fun onWatchRewardedAdClick() {
        _watchRewardedAdClicked.value = true
    }

    fun onWatchRewardedAdFinish() {
        _watchRewardedAdClicked.value = false
        _showRevealLetterDialog.value = false
        onDismissEarnPointsDialogClick()
    }


}