//
//  AppModel.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation

class AppModel: Identifiable, ObservableObject {
    var id: Int
    var iconUrl: String
    var name: String
    var description: String
    @Published var isFavorite: Bool
    init(data: [String : Any]) {
        id = data["trackId"] as! Int
        iconUrl = data["artworkUrl60"] as! String
        name = data["trackName"] as! String
        description = data["description"] as! String
        isFavorite = true
    }
}
