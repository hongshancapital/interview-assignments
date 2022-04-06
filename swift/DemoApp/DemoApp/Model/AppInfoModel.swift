//
//  AppInfoModel.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import Foundation
import SwiftUI

struct AppInfoModel: Decodable, DynamicProperty, Identifiable {
    
    var id: Int {
        return self.appId
    }
    
    enum AppInfoCodingKey: CodingKey {
        case trackId // -> AppId
        case artworkUrl60 // -> appLogoUrl
        case trackName // -> app name
        case description // description of app
    }
    let appId: Int // app uique id
    let appLogoUrl: String // app logo url string
    let appName: String // app logo url string
    let description: String // app logo url string
    var  isFavorite: Bool // isFavorite storage by local
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: AppInfoCodingKey.self)
        self.appId = try container.decode(Int.self, forKey: .trackId)
        self.appLogoUrl = try container.decode(String.self, forKey: .artworkUrl60)
        self.appName = try container.decode(String.self, forKey: .trackName)
        self.description = try container.decode(String.self, forKey: .description)
        self.isFavorite = false
    }
    
    init(appId: Int, appLogoUrl: String, appName: String, description: String, isFavorite: Bool) {
        self.appId = appId
        self.appLogoUrl = appLogoUrl
        self.appName = appName
        self.description = description
        self.isFavorite = isFavorite
    }
}
