var express = require('express');
var mongoClient = require('mongodb').MongoClient;
var router = express.Router();''
var db

const CONNECTION_URL = "mongodb+srv://dodge_admin:dodgeadmin@cluster0-nvoz9.gcp.mongodb.net/test?retryWrites=true&w=majority";
const DATABASE_NAME = "Dodge";
const DATABASE_COLLECTION = "DodgeGames";


mongoClient.connect(CONNECTION_URL, (err, client) => {
  if (err) return console.log("error happened here" + err)
  db = client.db(DATABASE_NAME) // connect to database
  collection = db.collection("DodgeGames");



router.get('/state', (req, res) => {
	console.log(req.body);
	//gets the req.body.game_id and returns all information for it
	  res.send(req.body);

});

router.post('/update', (req, res) => {
	console.log(req.body);
	// finds game id and then the user id and then updates the entier user position and alive status
	  res.send(req.body);

});

router.get('/testDB', (req, res) => {
	//gets the req.body.game_id and returns all information for it
	collection.find({}).toArray((error, result) => {
        if(error) {
            return res.status(500).send(error);
        }
        res.send(result);
    });
});

router.post('/testPost', (req, res) => {
  res.send(req.body);
});

/* GET home page. */
router.get('/testGet', (req, res) => {
  res.render('index', { title: 'Express' });
});


  })

module.exports = router;