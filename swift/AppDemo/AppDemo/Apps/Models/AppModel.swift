//
//  AppModel.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import Foundation


struct AppModel: Codable, Identifiable {
    var id: String {
        return bundleId
    }
    var bundleId: String
    var trackCensoredName: String?
    var artworkUrl60: String?
    var description: String?
}

struct ListModel<Model: Codable>: Codable {
    var resultCount: Int?
    var results: [Model]?
}
