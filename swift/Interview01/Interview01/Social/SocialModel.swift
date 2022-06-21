//
//  SocialModel.swift
//  Interview01
//
//  Created by chenzhe on 2022/6/21.
//

import Foundation

struct SocialModel: Codable, Identifiable {
    var id: String
    let coverurl: String
    let title: String
    let bio: String
    var isLike: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "currentVersionReleaseDate", coverurl = "artworkUrl512", title = "trackCensoredName", bio = "description"
        
    }
    
}


