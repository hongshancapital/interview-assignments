//
//  Software.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/20.
//

import Foundation

struct Software: Codable, Equatable, Identifiable {
    var id: Int { trackId }

    let trackId: Int
    /// 应用名
    let trackCensoredName: String
    /// 公司名
    let artistName: String
    /// 应用图标
    let artworkUrl100: String
    /// 是否喜欢
    var isLike: Bool = false
    
    static var `default` = try! Software.fromJson(data: Data(contentsOf: Bundle.main.url(forResource: "Software.json", withExtension: nil)!))
    
    init(trackId: Int, trackCensoredName: String, artistName: String, artworkUrl100: String, isLike: Bool = false) {
        self.trackId = trackId
        self.trackCensoredName = trackCensoredName
        self.artistName = artistName
        self.artworkUrl100 = artworkUrl100
        self.isLike = isLike
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.trackId = try container.decode(Int.self, forKey: .trackId)
        self.trackCensoredName = try container.decode(String.self, forKey: .trackCensoredName)
        self.artistName = try container.decode(String.self, forKey: .artistName)
        self.artworkUrl100 = try container.decode(String.self, forKey: .artworkUrl100)
    }
}
