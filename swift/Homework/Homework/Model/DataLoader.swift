//
//  DataLoader.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import Foundation

final class DataLoader: ObservableObject {
    var artworks: [Artwork] {
        let response: Response = load("1.json")
        return response.results
    }

    func loadArtworks() async -> [Artwork] {
        await Task {
            return artworks
        }.value
    }
}

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
