//
//  OTAppViewModel.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import Foundation

/**
 https://itunes.apple.com/search?entity=software&limit=10&term=chat&offset=10
 1，刷新
 2，加载更多
 3，点赞
 */



@MainActor class OTAppViewModel: ObservableObject {
    
    let requestPath = "https://itunes.apple.com/search"
    
    var requestParams: OTNetworkParams = ["entity":"software", "limit":10, "term":"chat"]
    
    
    @Published var appModelList: [AppModel] = [AppModel]()
    @Published var hasMoreData: Bool = false
    
    var offset = 0
    

    //MARK: Api
    
    func refresh() {
        Task {
            do {
                let appData: AppData = try await OTNetwork.shared.getData (from: requestPath, params: requestParams)
                appModelList = appData.results
                print("appModeLsit \(appModelList)")
                offset=appModelList.count
            } catch {
                
            }
        }
    }
    
    func loadMoreData() {
        Task {
            do {
                var loadMoreParams = requestParams
                loadMoreParams["offset"] = offset
                let appData: AppData = try await OTNetwork.shared.getData (from: requestPath, params: loadMoreParams)
                appModelList.append(contentsOf:appData.results)
                print("appModeLsit \(appModelList)")
                offset=appModelList.count
            } catch {
                
            }
        }
    }
    
    func favoriteApp() {
        
    }
    
    //MARK: Helper
    
    
}
