import Foundation

struct CardModel: Codable {
    let bundleId: String
    let artistName: String
    let description: String
    let artworkUrl60: URL
}

struct CardListModel: Codable {
    let resultCount: Int
    let results: [CardModel]
}
