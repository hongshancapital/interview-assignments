const mongoose = require('mongoose')
const shortId = require('shortid');

// database schema
const shortUrlSchema = new mongoose.Schema({
    // id
    _id: {type: String},
    url:{
        type: String,
        required: true
    },
    hash:{
        type: String,
    }
})

module.exports = mongoose.model('URL', shortUrlSchema);