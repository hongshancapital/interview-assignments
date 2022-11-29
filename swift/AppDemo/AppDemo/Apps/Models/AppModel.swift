//
//  AppModel.swift
//  AppDemo
//
//  Created by jaly on 2022/11/28.
//

import Foundation


struct AppModel: Codable {
    var bundleId: String
    var trackCensoredName: String?
    var artworkUrl60: String?
    var description: String?
}
