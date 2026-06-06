//
//  AppMessageModel.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import Foundation
struct AppMessageModel: Hashable, Identifiable {
    var id: Int
    var trackName: String
    var artworkUrl60: String
    var description: String
    var isFavorite: Bool = false
}
