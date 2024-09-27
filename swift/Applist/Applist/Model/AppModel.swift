//
//  AppModel.swift
//  Applist
//
//  Created by santcool on 2023/1/22.
//

import Foundation
import SwiftUI

struct ResponseModel: Decodable {
    let resultCount: Int
    let results: [AppModel]
}

struct AppModel: Decodable {
    let trackId: Int
    let trackCensoredName: String
    let description: String
    let artworkUrl100: String
}
