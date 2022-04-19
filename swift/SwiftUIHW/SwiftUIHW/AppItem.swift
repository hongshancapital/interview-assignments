//
//  AppItem.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/14/22.
//

import Foundation

struct AppItem: Decodable {
    var trackId: Int
    var artworkUrl100: String
    var trackName: String
    var sellerName: String
    var description: String
    var liked: Bool = false
    
    private enum CodingKeys: String, CodingKey {
        case trackId, artworkUrl100, trackName, sellerName, description
    }
}

struct DataResponse: Decodable {
    var resultCount: Int
    var results: [AppItem]
}
