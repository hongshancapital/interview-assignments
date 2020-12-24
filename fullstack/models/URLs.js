const mongoose = require('mongoose')

// database schema
const shortUrlSchema = new mongoose.Schema({
    // id
    _id: {type: String},
    url:{
        type: String,
        required: true
    },
    // short URL hash
    hash:{
        type: String,
    }
})

module.exports = mongoose.model('URL', shortUrlSchema);