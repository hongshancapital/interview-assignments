//
//  AppItem.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation

struct AppResult<T>: Codable where T: Codable {
    var resultCount: Int
    var results: [T]
}


struct AppItem: Identifiable, Codable {
    var id: Int = 0
    var title: String
    var body: String
    var favorite: Bool = false
    var imageURLString: String

    private enum CodingKeys: String, CodingKey {
        case title = "trackName"
        case body = "description"
        case imageURLString = "artworkUrl60"
    }
}
