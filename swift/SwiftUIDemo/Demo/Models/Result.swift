//
//  Result.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import Foundation
import SwiftUI

struct Results: Codable, Hashable {
    var resultCount: Int
    var results: [Result]?
}

struct Result: Codable, Hashable {
    var trackId: Int
    var trackName: String
    var description: String
    var artworkUrl60: String
}
