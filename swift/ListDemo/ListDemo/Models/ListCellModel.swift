//
//  ListCellModel.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import Foundation

// MARK: - JSON数据
struct ListDataModel: Codable {
    var resultCount: Int = 0
    var results: [ListCell] = []
}

struct ListCell: Codable {
    var trackId: Int = 0
    var artworkUrl60: String = ""
    var trackName: String = ""
    var description: String = ""
}

// MARK: - 加载状态
enum LoadingState: Int {
    case Loading = 0
    case LoadMore = 1
    case LoadComplete = 2
}
