const mongoose = require('mongoose');

const URLSchema = new mongoose.Schema({
    urlCode: String,
    originalUrl: String,
    shortUrl: String,
    createDate: {
        type: String,
        default: Date.now
    }
});

const Url = mongoose.model('Url', URLSchema);

export default Url;