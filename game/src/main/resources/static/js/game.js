localState = {
    currentUser: "",
    gameId: 0,
    role: 0,
    nextPlayer: "",
    gameIsReady: false
};

$(document).ready(function () {
        $("#start").click(function () {
            var playerName = $("#name").val();
            $.ajax({
                type: 'POST',
                url: "/api/game/player",
                data: JSON.stringify({
                    'playerName': playerName
                }),
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $("#login")[0].style.display = "none";
                    $("#game")[0].style.display = "block";
                    localState.gameId = response.gameId;
                    localState.currentUser = playerName;
                    localState.role = response.players.length - 1;
                    updateData(response);
                    updateState();
                },
                error: function (error) {
                    alert(error.responseText)
                }
            });
        });

        $(".firstPlayerPit").click(function () {
            makeTurn(this, 0);
        });

        $(".secondPlayerPit").click(function () {
            makeTurn(this, 1);
        });
    }
);

function makeTurn(pit, expectedRole) {
    if (!localState.gameIsReady) {
        alert("Wait till other player will join the game!");
        return;
    }
    if (localState.role === expectedRole) {
        if (localState.currentUser === localState.nextPlayer) {
            var pitId = $(pit).attr("id");
            var pitIndex = pitId.substr(3, pitId.length - 1);
            $.ajax({
                type: 'POST',
                url: "/api/game/turn",
                data: JSON.stringify({
                    'gameId': localState.gameId,
                    'pitIndex': pitIndex,
                    'playerName': localState.currentUser
                }),
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    localState.nextPlayer = response.nextPlayer;
                    updateData(response);
                },
                error: function (error) {
                    alert(error.responseText)
                }
            });
        } else {
            alert("It's not your turn now!");
        }
    } else {
        alert("It's not your side of board!");
    }
}

function updateState() {
    $.ajax({
        type: 'GET',
        url: "/api/game/state?gameId=" + localState.gameId + "&playerName=" + localState.currentUser,
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            updateData(response);
            if (response.finished) {
                alert("Game over! Player " + response.winner + " wins!")
            }
            updateState();
        },
        error: function () {
            // 1 second delay
            setTimeout(function () {
                updateState();
            }, 1000);
        }
    })
}

function updateData(gameState) {
    for (i = 0; i < gameState.pits.length; i++) {
        $("#pit" + i).text(gameState.pits[i]);
    }
    localState.nextPlayer = gameState.nextPlayer;
    $("#player1").text(gameState.players[0]);
    $("#player2").text(gameState.players[1]);
    if (gameState.players.length === 2) {
        localState.gameIsReady = true;
    }
    $("#nextPlayer").text(localState.nextPlayer);
}