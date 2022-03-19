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
    
    @Published var appModelList: [AppModel] = [AppModel]()
    @Published var hasMoreData: Bool = false
    var hasError: Bool = false
    var errorMessage: String?
    
    private let requestPath = "https://itunes.apple.com/search"
    private var requestOffset = 0

    //MARK: Api
    func refreshData() {
        let refreshParams: OTNetworkParams = ["entity": "software",
                                              "limit": 10,
                                              "term": "chat"]
        doRequest(with: refreshParams)
    }
    
    func loadMoreData() {
        let loadMoreParams: OTNetworkParams = ["entity": "software",
                                               "limit": 10,
                                               "term": "chat",
                                               "offset": requestOffset]
        doRequest(with: loadMoreParams)
    }
    
    func favoriteApp(id: Int) {
        let index = appModelList.firstIndex( where:  { $0.id == id })!
        appModelList[index].isFavorite.toggle()
    }
    
    //MARK: Helper
    
    private func appendAppModelList(with appendList: [AppModel]) {
        var temAppModelList = appModelList
        temAppModelList.append(contentsOf: appendList)
        
        var result = [AppModel]()
        
        for appModel in temAppModelList {
            if !result.contains(where: { item in
                return item == appModel
            }) {
                result.append(appModel)
            }
        }
        
      appModelList = result
    }
    
    private func doRequest(with requestParams: OTNetworkParams) {
        Task {
            do {
                let appData: AppData = try await OTNetwork.shared.getData (from: requestPath, params: requestParams)
                appendAppModelList(with: appData.results)
                requestOffset = appModelList.count
                //FIXME: 这里为了还原视频效果，手动限制加载数量
                hasMoreData = (requestOffset < 30)
                hasError = false
                errorMessage = nil
            } catch {
                dealRequestException(with: error)
            }
        }
    }
    
    private func dealRequestException(with error: Error) {
        hasMoreData = false
        hasError = true
        errorMessage = "request with Error: \(error.localizedDescription)"
    }
    
}
