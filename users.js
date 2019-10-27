var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var mongoClient = require('mongodb').MongoClient;

const CONNECTION_URL = "mongodb+srv://dodge_admin:dodgeadmin@cluster0-nvoz9.gcp.mongodb.net/test?retryWrites=true&w=majority";
const DATABASE_NAME = "Dodge";
const DATABASE_COLLECTION = "DodgeGames";


mongoClient.connect(CONNECTION_URL, (err, client) => {
  if (err) return console.log("Cannot access database: " + err)
  db = client.db(DATABASE_NAME) // connect to database
  collection = db.collection("DodgeGames");


// ADD TO DATABASE 
// https://docs.mongodb.com/guides/server/insert/
router.post('/join', (req, res) => {
	name = req.body.name; 
	// get game ids
    collection.find({}).toArray((error, result) => {
        if(error) {
            throw error;
        }
    });
    // res.send(name);

    // choose game to join or create new game and get team id and game id
    player_id = getRandomString(8); //TODO: write getRandomString method
    // check that player_id isn't already in play (while loop maybe)
    // add User to database
    // return user
    // res.send(player_id);

    // Make a dummy player
    this_team_id = 0;
    this_player_id = getRandomString(8);
    this_user = new User(this_team_id, this_player_id,name);
    // res.send(JSON.stringify(this_user));

    console.log(collection.length)

    // Look for an open game and add in player
    if (collection == undefined || collection.length == null) {
        collection = [];
        collection[0] = [];
        collection[0][0] = this_user;
        res.send(JSON.stringify(this_user));
    }
    else {
        var i = 0;
        for (i = 0; i < collection.length; i++) { //loop through all games
            this_game = collection[i];
            var j = this_game.length;

            //look through array for the user
            var k = 0;
            for (k = 0; k < j; k++) {
                if ((this_game[k].user_id.player_name) == (name)) {
                    console.log(this_game[k].user_id);
                    res.send(JSON.stringify(this_game[k]));

                }
            }

            if (j < 10) { //not full game
                this_team_id = (j % 2);
                collection[i][j] = this_user;
                res.send(JSON.stringify(this_user));
            }
            // }
        }
        // No open game so make a new game
        if (i == collection.length) { //the games boi is full
            this_team_id = 0;
            collection[i][0] = [this_user];
            res.send(JSON.stringify(this_user));
            return;
        }
    }
})

function getRandomString(length) {
        var result           = '';
        var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var charactersLength = characters.length;
        for ( var i = 0; i < length; i++ ) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }



/* GET users listing. */
router.get('/', function(req, res, next) {
	var text = JSON.stringify(new User( 1, "sdhacks", "e83hhfmso", "lydai" ))

  res.send(text);
});



class User {

    constructor( team_id, player_id, player_name){
    	var status = {};
    	var user_id = {};

    	status.team_id = team_id;
    	status.active = 3;

    	user_id.player_id = player_id;
    	user_id.player_name = player_name;


    	this.id = {};
    	this.id.status = status;
    	this.id.user_id = user_id;

    	this.position = { x : 10, y : 10 };
    }
}

    })

module.exports = router;
