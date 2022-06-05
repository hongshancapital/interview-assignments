//
//  IAAppInfoModel.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import Foundation

struct IAAppInfoModels: Codable {
    let resultCount: Int
    let results: [IAAppInfoModel]
}

struct IAAppInfoModel: Codable {
    let trackCensoredName: String
    let artworkUrl60: String
    let description: String

    // 是否喜欢
    var isFavorite = false

    enum CodingKeys: String, CodingKey  {
        case trackCensoredName, artworkUrl60, description
    }
}

extension IAAppInfoModel {
    var url: URL? { URL(string: artworkUrl60) }
}
