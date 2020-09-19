//
//  Decodable+Utilities.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import Foundation

extension Decodable {
    static func decode(from data: Data, dateDecodingStrategy: JSONDecoder.DateDecodingStrategy = .secondsSince1970) -> Self? {
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = dateDecodingStrategy
        do {
            return try decoder.decode(Self.self, from: data)
        } catch {
            guard let json = try? JSONSerialization.jsonObject(with: data, options: []) else { return nil }
            guard let data = try? JSONSerialization.data(withJSONObject: json, options: .prettyPrinted) else { return nil }
            guard let string = String(data: data, encoding: .utf8) else { return nil }
            print(string)
            print(error)
            return nil
        }
    }
}

