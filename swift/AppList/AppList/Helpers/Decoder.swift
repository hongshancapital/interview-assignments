//
//  Decoder.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

struct Decoder {
    
    func decode<T: Decodable>(data: Data) -> T {
        do {
            let decoder = JSONDecoder()
            return try decoder.decode(T.self, from: data)
        } catch {
            fatalError("Couldn't decode data as \(T.self):\n\(error)")
        }
    }
}
