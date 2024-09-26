//
//  AppInfo.swift
//  MyApps
//
//  Created by liangchao on 2022/4/16.
//

import Foundation

struct AppInfo: Codable {
    let trackName: String
    let description: String
    let artworkUrl100: String
    let trackId: Int
    let bundleId: String
}

extension AppInfo: Identifiable {
    var id: String { "\(trackId)" }
}
