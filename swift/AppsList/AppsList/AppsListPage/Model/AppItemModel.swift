//
//  AppItemModel.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/2.
//

import Foundation

struct AppItemModel: Codable, Identifiable {
    var id: Int {
        return trackId
    }
    
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl100: String
    
    var isFavorite: Bool {
        set {
            UserDefaults.standard.set(newValue, forKey: "AppLikeKey_\(trackId)")
        }
        get {
            UserDefaults.standard.bool(forKey: "AppLikeKey_\(trackId)")
        }
    }
    
    
    
    static var testItem: AppItemModel {
        AppItemModel(trackId: 1064216828, trackName: "Reddit", description: "Reddit is the place where people come together to have the most authentic and interesting conversations on the internet—Where gaming communities, nostalgic internet forums, bloggers, meme-makers, and fandoms mingle alongside video streamers, support groups, news junkies, armchair experts, seasoned professionals, and artists and creators of all types.", artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple123/v4/27/b8/45/27b845fc-bd76-16e8-994d-098e7dfd7773/AppIcon-0-0-1x_U007emarketing-0-0-0-10-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/100x100bb.jpg")
    }
}


