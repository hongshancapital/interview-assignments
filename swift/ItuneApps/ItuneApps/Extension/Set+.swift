//
//  Set+Extension.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Foundation

extension Set: RawRepresentable where Element: Codable {
    public var rawValue: String {
        guard let data = try? JSONEncoder().encode(self),
              let string = String(data: data, encoding: .utf8) else { return "" }
        return string
    }
    
    public init?(rawValue: String) {
        guard let data = rawValue.data(using: .utf8),
              let set = try? JSONDecoder().decode(Self.self, from: data) else {
            return nil
        }
        self = set
    }
}

