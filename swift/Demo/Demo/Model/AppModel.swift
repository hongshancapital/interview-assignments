//
//  AppModel.swift
//  Demo
//
//  Created by jyt on 2023/3/21.
//

import Foundation

struct AppItemList: Codable {
    var resultCount: Int
    var results: [AppItem]
}

struct AppItem: Codable, Identifiable {
    var id: Int
    var name: String
    var thumbnailURL: URL
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case name = "trackName"
        case thumbnailURL = "artworkUrl60"
        case description
    }
}

func loadJSON<T: Decodable>(_ filename: String) throws -> T {
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

    let decoder = JSONDecoder()
    return try decoder.decode(T.self, from: data)
}

func loadJSON<T: Decodable>(_ data: Data) throws -> T {
    let decoder = JSONDecoder()
    return try decoder.decode(T.self, from: data)
}
