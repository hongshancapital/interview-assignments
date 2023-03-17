//
//  AppInfo.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//  数据模型

import SwiftUI

struct AppInfo: Hashable, Codable {
    var trackId: Int
    var trackName: String
    var artworkUrl60: String
    var description: String
}

struct AppResult: Hashable, Codable {
    var resultCount: Int
    var results: [AppInfo]
}
