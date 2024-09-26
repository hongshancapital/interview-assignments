//
//  AppInfoModel.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import Foundation

// MARK: - 服务器返回模型


struct AppInfoResponse: Codable {
    
    let results: [AppInfo]
    
}

struct AppInfo: Codable {
    
    let trackId: Double
    
    let trackName: String
    
    let artworkUrl100: String
    
    let description: String?
    
}

// MARK: - 界面显示所需模型

struct AppInfoModel: Codable {
    
    let trackId: Double
    
    let trackName: String
    
    let artworkUrl100: String
    
    let description: String
    
    var isCollected: Bool
    
}



