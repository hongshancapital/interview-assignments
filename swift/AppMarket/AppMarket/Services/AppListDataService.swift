//
//  AppListDataService.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import Foundation

class AppListDataService {
    
    private var allAppInfos: [AppInfo] = []
    private var collectedApps: [Double] = []
    private var userDefaultCollectedAppsKey = "CollectedApps"
    
    var pageCount = 20
    
    init(){
        allAppInfos = loadMockData()
        collectedApps = fetchCollectedApps()
    }
    
    private func loadMockData() -> [AppInfo]{
        do {
            let data = try Data(contentsOf: Bundle.main.url(forResource: "MockData", withExtension: "json")!)
            let response = try JSONDecoder().decode(AppInfoResponse.self, from: data)
            return response.results
        } catch {
            print("Load mock data error.\(error)")
            return []
        }
    }
    
    private func fetchCollectedApps() -> [Double]{
        
        guard let array = UserDefaults.standard.array(forKey: userDefaultCollectedAppsKey),
              let collectedApps = array as? [Double]
        else{
            print("Fail to fetch collected Apps.")
            return []
        }
        return collectedApps
    }
    
    func updateCollectedApps(appInfo: AppInfoModel){
        
        if let index = collectedApps.firstIndex(of: appInfo.trackId) {
            collectedApps.remove(at: index)
        } else {
            collectedApps.append(appInfo.trackId)
        }
        
        UserDefaults.standard.set(collectedApps, forKey: userDefaultCollectedAppsKey)
        
    }
    
    
    func fetchAppInfos(page: Int = 0) async -> [AppInfoModel] {
        
        guard page >= 0,
              pageCount > 0,
              !allAppInfos.isEmpty
        else {
            return []
        }
        
        // 确保取出的数据不会越界
        let returnedStarIndex = page * pageCount < 0 ? 0 : page * pageCount
        let returnedEndIndex = (page + 1) * pageCount - 1 > allAppInfos.count - 1 ? allAppInfos.count - 1 : (page + 1) * pageCount - 1
        guard returnedEndIndex >= returnedStarIndex else {
            return []
        }
        
        try? await Task.sleep(nanoseconds: 2_000_000_000)
        
        guard returnedEndIndex != returnedStarIndex else {
            let appInfo = allAppInfos[returnedEndIndex]
            return [AppInfoModel(
                trackId: appInfo.trackId,
                trackName: appInfo.trackName,
                artworkUrl100: appInfo.artworkUrl100,
                description: appInfo.description ?? "",
                isCollected: collectedApps.contains(appInfo.trackId)
            )]
        }
        
        return allAppInfos[returnedStarIndex...returnedEndIndex]
            .map { appInfo in
                AppInfoModel(
                    trackId: appInfo.trackId,
                    trackName: appInfo.trackName,
                    artworkUrl100: appInfo.artworkUrl100,
                    description: appInfo.description ?? "",
                    isCollected: collectedApps.contains(appInfo.trackId)
                )
            }
        
    }
  
}
