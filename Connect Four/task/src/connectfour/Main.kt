package connectfour

const val SPACE = " "

var invalidInput = false
var playerChoice = ""

var boardEncoding: List<MutableList<String>> = emptyList()
var topColNumRow = SPACE

var bottomRow = ""

var columns: Int = 7

var lowestAvailableRow: Int = Int.MIN_VALUE
var player1turn = true

var gameNumber = 1

fun announceNewGame() {
    if (numberOfGames!! > 1) println("Game #$gameNumber")
}

private fun showMessage(player1: String, player2: String, rows: Int, columns: Int) {
    println(
        """
$player1 VS $player2
$rows X $columns board
${
            if (numberOfGames == 1) "Single game"
            else "Total $numberOfGames games"
        }
        """.trimIndent()
    )
    if (numberOfGames!! > 1) announceNewGame()
}

fun drawGameBoard() {
    val vertical = "║" // "|"

    var boardCore = ""
    for (index in boardEncoding.indices) {
        for (cell in boardEncoding[index]) {
            boardCore += vertical + cell
        }
        boardCore += vertical + "\n"
    }

    println(topColNumRow)
    print(boardCore)
    println(bottomRow)
}

fun createEmptyBoard() {
    boardEncoding = List(rows) { MutableList(columns) { SPACE } }
}

private fun startGame(player1: String, player2: String, rows: Int, columns: Int) {
    showMessage(player1, player2, rows, columns)
    createEmptyBoard()

    repeat(columns) {
        topColNumRow += "${it + 1} "
    }

    val bottomLeftCorner = "╚" // "="
    val horizontal = "═" // "="
    val intersection = "╩" // "="
    val bottomRightCorner = "╝" // "="

    bottomRow = bottomLeftCorner + horizontal + (intersection + horizontal).repeat(columns - 1) + bottomRightCorner

    drawGameBoard()
}

fun isColumnFull(chosenColumn: Int): Boolean {
    for (index in boardEncoding.lastIndex downTo 0) {
        val row = boardEncoding[index]
        if (row[chosenColumn - 1] == SPACE) {
            lowestAvailableRow = index
            return false
        }
    }
    return true
}

var rows: Int = 6

private fun dimensionInquiry(player1: String, player2: String) {
    println("Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7)")
    val boardDimensions = readLine()!!.lowercase().replace(" ", "").replace("\t", "") // "6x8"

    val string = "\\d+ ?x ?\\d+"
    val regex = string.toRegex()
    val regexConstructor = Regex(string)

    if (boardDimensions.isEmpty()) {
        rows = 6
        columns = 7

        invalidInput = false

    } else if (boardDimensions.matches(regexConstructor)) {
        rows = boardDimensions.substringBefore("x").toInt()
        columns = boardDimensions.substringAfter("x").toInt()

        if (rows !in 5..9) {
            invalidInput = true
            println("Board rows should be from 5 to 9")
        } else if (columns !in 5..9) {
            invalidInput = true
            println("Board columns should be from 5 to 9")
        } else {
            invalidInput = false
        }
    } else {
        invalidInput = true
        println("Invalid input")
    }
}

var spaceLeftOnBoard = true

fun checkBoardFull(): Boolean {
    for (row in boardEncoding) {
        if (SPACE in row) return true
    }
    return false
}

var winnerDecided = false
var player1Won = false

const val player1Token = "o"
const val player2Token = "*"

fun checkWin(): Boolean {
    for (row in boardEncoding.indices) {
        for (column in boardEncoding[row].indices) {
            /**
             * horizontal win
             */
            if (column + 2 < boardEncoding[row].lastIndex && (boardEncoding[row][column] == player1Token || boardEncoding[row][column] == player2Token) && boardEncoding[row][column] == boardEncoding[row][column + 1] && boardEncoding[row][column] == boardEncoding[row][column + 2] && boardEncoding[row][column] == boardEncoding[row][column + 3]) {
                player1Won = boardEncoding[row][column] == player1Token
                return true
            }

            /**
             * vertical win
             */
            else if (row + 2 < boardEncoding.lastIndex && (boardEncoding[row][column] == player1Token || boardEncoding[row][column] == player2Token) && boardEncoding[row][column] == boardEncoding[row + 1][column] && boardEncoding[row][column] == boardEncoding[row + 2][column] && boardEncoding[row][column] == boardEncoding[row + 3][column]) {
                player1Won = boardEncoding[row][column] == player1Token
                return true
            }

            /**
             * diagonal win \
             */
            else if (row + 2 < boardEncoding.lastIndex && column + 2 < boardEncoding[row].lastIndex && (boardEncoding[row][column] == player1Token || boardEncoding[row][column] == player2Token) && boardEncoding[row][column] == boardEncoding[row + 1][column + 1] && boardEncoding[row][column] == boardEncoding[row + 2][column + 2] && boardEncoding[row][column] == boardEncoding[row + 3][column + 3]) {
                player1Won = boardEncoding[row][column] == player1Token
                return true
            }

            /**
             * diagonal win /
             */
            else if (row - 2 > 0 && column + 2 < boardEncoding[row].lastIndex && (boardEncoding[row][column] == player1Token || boardEncoding[row][column] == player2Token) && boardEncoding[row][column] == boardEncoding[row - 1][column + 1] && boardEncoding[row][column] == boardEncoding[row - 2][column + 2] && boardEncoding[row][column] == boardEncoding[row - 3][column + 3]) {
                player1Won = boardEncoding[row][column] == player1Token
                return true
            }
        }
    }
    return false
}

var numberOfGames: Int? = 1

var invalidNumberOfGames = false

fun playCountInquiry() {
    println(
        """
Do you want to play single or multiple games?
For a single game, input 1 or press Enter
Input a number of games:
        """.trimIndent()
    )
    val numberInput = readLine()!! // "X" //  readLine()!!
    numberOfGames = numberInput.toIntOrNull()
    if (numberInput.isEmpty()) {
        invalidNumberOfGames = false
        numberOfGames = 1
    } else {
        try {
            numberOfGames = numberInput.toInt()
            if (numberOfGames!! < 1) {
                invalidNumberOfGames = true
                println("Invalid input")
            } else {
                invalidNumberOfGames = false
            }
        } catch (e: NumberFormatException) {
            invalidNumberOfGames = true
            println("Invalid input")
        }
    }
}

fun playGame(player1: String, player2: String, columns: Int) {
    var invalidColumn: Boolean

    if (playerChoice != "end") {
        do {
            println("${if (player1turn) player1 else player2}'s turn:")
            playerChoice = readLine()!!
            if (playerChoice == "end") {
                return
            } else {
                val chosenColumn = playerChoice.toIntOrNull()

                if (chosenColumn == null) {
                    invalidColumn = true
                    println("Incorrect column number")
                } else {
                    if (chosenColumn in 1..columns) {
                        if (isColumnFull(chosenColumn)) {
                            invalidColumn = true
                            println("Column $chosenColumn is full")
                        } else {
                            invalidColumn = false
                            val currentPlayerToken = if (player1turn) player1Token else player2Token
                            boardEncoding[lowestAvailableRow][chosenColumn - 1] = currentPlayerToken
                            winnerDecided = checkWin()
                            spaceLeftOnBoard = checkBoardFull()
                        }
                    } else {
                        invalidColumn = true
                        println("The column number is out of range (1 - $columns)")
                    }
                }
            }
        } while (invalidColumn)
    }

    player1turn = !player1turn
    if (playerChoice != "end") drawGameBoard()
}

var player1Score = 0
var player2Score = 0

fun main() {
    println("Connect Four")

    println("First player's name:")
    val player1 = readLine()!! // "Mia" // readLine()!!

    println("Second player's name:")
    val player2 = readLine()!! // "Bill" // readLine()!!

    do {
        dimensionInquiry(player1, player2)
    } while (invalidInput)

    do {
        playCountInquiry()
    } while (invalidNumberOfGames)

    startGame(player1, player2, rows, columns)

    do {
        if (gameNumber > 1) {
            announceNewGame()
            createEmptyBoard()
            drawGameBoard()
        }
        do {
            playGame(player1, player2, columns)
        } while (playerChoice != "end" && !winnerDecided && spaceLeftOnBoard)
        ++gameNumber
        if (winnerDecided) {
            println(
                """
Player ${
                    if (player1Won) {
                        player1Score += 2
                        player1
                    } else {
                        player2Score += 2
                        player2
                    }
                } won
${
                    if (numberOfGames!! > 1) {
                        """Score
$player1: $player1Score $player2: $player2Score
                            """.trimIndent()
                    } else ""

                }
            """.trimIndent()
            )
        } else if (!spaceLeftOnBoard) {
            ++player1Score
            ++player2Score
            println("It is a draw")
            print(
                """
${
                    if (numberOfGames!! > 1) {
                        """Score
$player1: $player1Score $player2: $player2Score
                            """.trimIndent() + "\n"
                    } else ""
                }
            """.trimIndent()
            )
        }
    } while (gameNumber <= numberOfGames!!)
    println("Game over!")
}
