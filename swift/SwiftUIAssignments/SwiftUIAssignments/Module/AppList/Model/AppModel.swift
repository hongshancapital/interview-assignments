//
//  AppModel.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import Foundation

struct AppListModel {
    /// total for app
    var total: Int
    /// current page
    var currentPage: Int
    
    var appModels: [AppModel]
}

///
///
/// model
struct AppModel: Identifiable {

    let id: Int

    /// app icon
    let artworkUrl100: String

    /// app name
    let trackCensoredName: String

    /// app description
    let description: String

    /// if like
    var isLike: Bool = false
}

// MARK: - Decodable
extension AppModel: Decodable {

    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case artworkUrl100, trackCensoredName, description
    }
}


// MARK: - Property
extension AppModel {

    /// app icon url
    var artworkUrl: URL? {
        URL(string: artworkUrl100)
    }
}


extension AppModel {
    /// mock model for preview or test
    static let mock = AppModel(
        id: 1064216828,
        artworkUrl100: "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/da/78/50/da78505b-ac60-5252-da78-24012bee84b2/AppIcon-1x_U007emarketing-0-7-0-85-220.png/100x100bb.jpg",
        trackCensoredName: "Reddit",
        description: "Reddit is the place where people come together to have the most authentic and interesting conversations on the internet—Where gaming communities"
    )
}
