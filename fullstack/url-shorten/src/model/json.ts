export default interface MSG {
    Code: number
    Data?: Data
    Text: string
}

interface Data {
    longUrl: string
    shortUrl: string
}
