//
//  AppInfoModel.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
import SwiftUI
public struct AppInfoResponse: Codable {
    let resultCount: Int
    let results: [AppInfo]?
    init(resultCount: Int, results: [AppInfo]?) {
        self.resultCount = resultCount
        self.results = results
    }
}

// MARK: - Result

struct AppInfo: Codable {
    let artworkUrl: String?
    let trackName: String?
    let appDescription: String?
    var isCollected: Bool = false
    let trackId: Int
    enum CodingKeys: String, CodingKey {
        case artworkUrl = "artworkUrl60"
        case appDescription = "description"
        case trackName
        case trackId
    }

    init(artworkUrl: String?, trackName: String?, appDescription: String?, isCollected: Bool, trackId: Int) {
        self.artworkUrl = artworkUrl
        self.trackName = trackName
        self.appDescription = appDescription
        self.isCollected = isCollected
        self.trackId = trackId
    }
}

extension AppInfo: Identifiable {
    var id: Int { trackId }
}

extension AppInfo: Equatable {
    static func == (lhs: AppInfo, rhs: AppInfo) -> Bool {
        return lhs.trackId == rhs.trackId && lhs.isCollected == rhs.isCollected
    }
}

enum Rating: String, Codable {
    case the12 = "12+"
    case the17 = "17+"
    case the4 = "4+"
    case the9 = "9+"
}

enum Currency: String, Codable {
    case usd = "USD"
}

enum Feature: String, Codable {
    case gameCenter
    case iosUniversal
}

enum FormattedPrice: String, Codable {
    case free = "Free"
}

enum Kind: String, Codable {
    case software
}
