//
//  Bundle+Helper.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import Foundation

extension Bundle {
    static func loadBundledJSON<T: Decodable>(file: String) -> T {
        guard let url = main.url(forResource: file, withExtension: "json") else {
            fatalError("Resource not found: \(file)")
        }
        return try! loadJSON(from: url)
    }

    static func loadJSON<T: Decodable>(from url: URL) throws -> T {
        let data = try Data(contentsOf: url)
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return try decoder.decode(T.self, from: data)
    }
}
