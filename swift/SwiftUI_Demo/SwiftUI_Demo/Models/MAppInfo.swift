//
//  MAppInfo.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import Foundation

///网络请求返回的数据源数组
struct MInfoResponse: Codable {
    let results: [MAppInfo]
}


///网络返回数据源Model
struct MAppInfo: Codable {
    let trackId: Double
    let trackName: String
    let artworkUrl100: String
    let description: String?
}


///用于视图展示的Model
struct MViewInfo: Codable {
    let trackId: Double
    let trackName: String
    let artworkUrl100: String
    let description: String
    var isSelected: Bool
}
