//
//  ResourceResponse.swift
//  FavoriteApps
//
//  Created by 刘明星 on 2022/5/6.
//

import Foundation

struct ResourceResponse: Codable {
    let resultCount: Int
    let results: [AppInfo]
    
    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}
