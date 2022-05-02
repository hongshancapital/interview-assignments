//
//  Home.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import Foundation

struct Home:Codable, Equatable, Identifiable {
    public let id: Int
    let imageUrl: String
    let title: String
    let content: String
    var isFavorite: Bool
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case imageUrl = "imageUrl"
        case title = "title"
        case content = "content"
        case isFavorite = "isFavorite"
   }
}

struct HomeList: Codable {
    let list: [Home]
    let total: Int
    let count: Int
    enum CodingKeys: String, CodingKey {
        case list = "list"
        case total = "total"
        case count = "count"
    }
}
