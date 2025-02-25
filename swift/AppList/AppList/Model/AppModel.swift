//
//  AppModel.swift
//  AppList
//
//  Created by wozyao on 2022/10/25.
//

import Foundation

struct AppModel: Identifiable, Codable {
    var id: Int
    var artworkUrl100: String
    var trackName: String
    var description: String
    var isLike = false
    
    private enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case artworkUrl100
        case trackName
        case description
    }
}

struct Response: Codable {
    var resultCount: Int
    var results: [AppModel]
}

let response: Response = load("appData.json")
let dataSource: [AppModel] = response.results

func load<T: Decodable>(_ filename: String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else {
            fatalError("Couldn't find \(filename) in main bundle.")
    }
    
    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
