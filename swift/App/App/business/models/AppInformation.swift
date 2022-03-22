//
//  AppInformation.swift
//  App
//
//  Created by august on 2022/3/21.
//

import Foundation
import SwiftUI

struct HomeListResponse: Decodable {
    let resultCount: Int
    let results: [AppInformation]
}

struct AppInformation: Decodable {
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl60: URL
}
