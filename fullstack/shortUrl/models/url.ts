import { model, Schema } from "mongoose"

const UrlSchema: Schema = new Schema({
    fullUrl: {
        type: String,
        require: true
    },
    shotUrlCode: {
        type: String,
        require: true
    },
})

module.exports = model("Url", UrlSchema)
