//
//  AppInfoModel.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import Foundation

/// APP信息模型
struct AppInfoModel: Decodable, Identifiable {
    var id = UUID()
    
    /// 应用id
    let appId: Int
    
    /// 应用名称
    let name: String
    
    /// 应用Logo图标
    var logoUrl: String
    
    /// 应用描述
    let description: String
    
    /// 是否标记为喜欢
    var liked: Bool
    
    enum CodingKeys: String, CodingKey {
        case appId = "trackId"
        case name = "trackName"
        case logoUrl = "artworkUrl100"
        case description
        case liked = "isGameCenterEnabled"
    }
}
