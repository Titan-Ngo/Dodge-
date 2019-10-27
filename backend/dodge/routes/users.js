var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");

router.post('/join', (req, res) => {
	name = req.body.name; 
	// get game ids
	// choose game to join or create new game and get team id and game id
	player_id = getRandomString(8); //TODO: write getRandomString method
	// check that player_id isn't already in play (while loop maybe)
	// add User to database
	// return user
})


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
    	status.active = true;

    	user_id.player_id = player_id;
    	user_id.player_name = player_name;


    	this.id = {};
    	this.id.status = status;
    	this.id.user_id = user_id;

    	this.position = { x : 10, y : 10 };
    }
}

module.exports = router;
