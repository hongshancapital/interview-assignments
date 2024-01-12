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


@MainActor
class OTAppViewModel: ObservableObject {
    @Published var appModelList: [AppModel] = [AppModel]()
    @Published var hasMoreData: Bool = false
    var hasError: Bool = false
    var errorMessage: String?
    var favoriteIds: [Int] = [Int]()
    
    private let requestPath = "https://itunes.apple.com/search"
    private var requestOffset = 0
    
    //FIXME: 这里为了还原视频效果，手动限制加载数量
    private let appModelCountLimit = 30
    
    //MARK: Api
    func refreshData() async {
        let refreshParams: OTNetworkParams = ["entity": "software",
                                              "limit": 10,
                                              "term": "chat"]
        await doRequest(with: refreshParams, isRefresh: true)
    }
    
    func loadMoreData() async {
        let loadMoreParams: OTNetworkParams = ["entity": "software",
                                               "limit": 10,
                                               "term": "chat",
                                               "offset": requestOffset]
        await doRequest(with: loadMoreParams)
    }
    
    func favoriteApp(id: Int) {
        if let index = appModelList.firstIndex(where:  { $0.id == id }) {
            appModelList[index].isFavorite.toggle()
        }
        
        if let favoriteIndex = favoriteIds.firstIndex(where: { $0 == id }) {
            favoriteIds.remove(at: favoriteIndex)
        } else {
            favoriteIds.append(id)
        }
    }
    
    //MARK: Helper
    //添加offset后接口请求到的有重复数据，添加前需要先过滤
    private func mergeAppModelList(with appendList: [AppModel], isRefresh: Bool) {
        var headList: [AppModel]
        var tailList: [AppModel]
        
        //刷新时，原始list重置，新list不需要过滤
        if isRefresh {
            headList = [AppModel]()
            tailList = appendList
        } else {
            headList = appModelList
            tailList = appendList.filter { item in
                return headList.firstIndex(where: { $0.id == item.id }) == nil
            }
        }
        
        //恢复点赞状态
        favoriteIds.forEach { id in
            if let index = tailList.firstIndex(where: { $0.id == id }) {
                tailList[index].isFavorite.toggle()
            }
        }
        
        headList.append(contentsOf: tailList)
        //只赋值一次，避免多次外界view多次刷新
        appModelList = headList
    }
    
    private func doRequest(with requestParams: OTNetworkParams, isRefresh: Bool = false) async {
        do {
            let appData: AppData = try await OTNetwork.shared.getData(from: requestPath, params: requestParams)
            mergeAppModelList(with: appData.results, isRefresh: isRefresh)
            requestOffset = appModelList.count
            //FIXME: 这里为了还原视频效果，手动限制加载数量
            hasMoreData = (requestOffset < appModelCountLimit)
            hasError = false
            errorMessage = nil
        } catch {
            dealRequestException(with: error)
        }
    }
    
    private func dealRequestException(with error: Error) {
        hasError = true
        errorMessage = "request with Error: \(error.localizedDescription)"
    }
    
}
