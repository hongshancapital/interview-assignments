//
//  UnitTesting_CollectedAppsHelper.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/31.
//

import Foundation
@testable import AppMarket


class UnitTesting_CollectedAppsHelper {
    
    static let userDefaultCollectedAppsKey = "CollectedApps"
    static let oldCollectedApps = fetchCollectedApps()
    
    static func loadMockData() -> [AppInfo]{
        do {
            let data = try Data(contentsOf: Bundle.main.url(forResource: "MockData", withExtension: "json")!)
            let response = try JSONDecoder().decode(AppInfoResponse.self, from: data)
            return response.results
        } catch {
            print("Load mock data error.\(error)")
            return []
        }
    }
    
    
    static func fetchCollectedApps() -> [Double]{
        
        guard let array = UserDefaults.standard.array(forKey: userDefaultCollectedAppsKey),
              let collectedApps = array as? [Double]
        else{
            print("Fail to fetch collected Apps.")
            return []
        }
        return collectedApps
    }
    
    // 还原测试前本地收藏的app列表
    static func reductOldCollectedApps() {
        UserDefaults.standard.set(oldCollectedApps, forKey: userDefaultCollectedAppsKey)
    }
    
    
    static func simpleAppInfoModel() -> AppInfoModel {
        AppInfoModel(
            trackId: 1163852619,
            trackName: "Google Chat",
            artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/06/b2/84/06b284d5-e33f-7a28-c773-1ef86a5c5418/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/100x100bb.jpg",
            description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
            isCollected: true
        )
    }
    
    
}
