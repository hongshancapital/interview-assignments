//
//  HSAppViewModel.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//

import Foundation

@MainActor
class HSAppViewModel: ObservableObject {
    @Published var modelList: [HSAppModel] = []
    @Published var hasMore: Bool = false
    var isRefreshData: Bool = false
    var hasError: Bool = false
    var errorMessage: String?
    var likedList: [Int] = []
    private let requestAPI = "https://itunes.apple.com/search"
    private var loadMoreOffset = 0
    private let appModelTotalCount = 20
    private let requestNum = 10
    private let entityName = "software"
    private let termName = "chat"
    
    func getAppData() async {
        let params: HSNetworkParams = ["entity": entityName,
                                       "limit": requestNum,
                                       "term": termName]
        isRefreshData = true;
        await requestMake(with: params)
    }
    
    func loadMoreData() async {
        let params: HSNetworkParams = ["entity": entityName,
                                       "limit": requestNum,
                                       "term": termName,
                                       "offset": loadMoreOffset]
        isRefreshData = false;
        await requestMake(with: params)
    }
    
    private func requestMake(with requestParams: HSNetworkParams) async {
        do {
            let appData: HSAppDataSource = try await HSNetWork.shared.requestAppData(from: requestAPI, params: requestParams)
            mergeDataList(result: appData.results)
            updateMoreTag()
            hasError = false
            errorMessage = nil
        } catch {
            hasError = true
            errorMessage = "request with Error: \(error.localizedDescription)"
        }
    }
    
    private func mergeDataList(result dataList: [HSAppModel]) {
        var currentDataList = [HSAppModel]()
        var newDataList: [HSAppModel]
        
        if isRefreshData {
            newDataList = dataList
        } else {
            currentDataList = modelList
            newDataList = dataList.filter { item in
                return currentDataList.firstIndex(where: { $0.id == item.id }) == nil
            }
        }
    
        likedList.forEach { id in
            if let index = newDataList.firstIndex(where: { $0.id == id }) {
                newDataList[index].isFavorite.toggle()
            }
        }
        currentDataList.append(contentsOf: newDataList)
        modelList = currentDataList
    }
    
    private func updateMoreTag() {
        loadMoreOffset = modelList.count
        hasMore = (loadMoreOffset < appModelTotalCount)
    }
    
    func likeApp(id: Int) {
        if let index = modelList.firstIndex(where:  { $0.id == id }) {
            modelList[index].isFavorite.toggle()
        }
        if let favoriteIndex = likedList.firstIndex(where: { $0 == id }) {
            likedList.remove(at: favoriteIndex)
        } else {
            likedList.append(id)
        }
    }
}

