//
//  File.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import Foundation

struct ListItemModel: Identifiable, Codable {
    let id = UUID().uuidString
    let imagePath: String
    let title: String
    let info: String
    var isFavourite: Bool
    let trackId: Int
}
