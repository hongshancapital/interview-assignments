let mongoose = require('mongoose');
const schema  = new mongoose.Schema({
    createAt:Date,
    longUrl:String,
    shortId:String,

});

schema.index({shortId:1},{unique:true});

export default mongoose.model('shortUrl',schema);