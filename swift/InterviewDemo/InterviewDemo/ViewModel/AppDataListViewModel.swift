//

//
//  AppDataListViewModel.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//
    

import Foundation
import Combine

class AppDataListViewModel: ObservableObject {
    
    // 是否正在刷新
    @Published var isRefreshing: Bool = false {
        didSet {
            if isRefreshing {
                index = 0
                getAppDataList(true)
            }
        }
    }
    // 是否正在加载更多
    @Published var isLoadingMore: Bool = false {
        didSet {
            if isLoadingMore {
                getAppDataList()
            }
        }
    }
    
    // 是否已加载过数据
    @Published var isDataLoaded: Bool = false
    // 是否有更多数据
    @Published var isNoMore: Bool = false
    
    // 数据列表
    @Published var appDataList: [AppDataRowViewModel] = []
    // 取消令牌
    var cancellationToken: AnyCancellable?
    
    var index = 0
    var size = 10
    
    init() {
        getAppDataList()
    }
    
    // 获取数据，isRefresh表示是否刷新，默认为false不刷新
    func getAppDataList(_ isRefresh: Bool = false) {
        
        // 请求数据
        // 构建查询
        // https://itunes.apple.com/search?entity=software&term=chat&limit=10&offset=0
        let queryItems = [
            URLQueryItem(name: "entity", value: "software"),
            URLQueryItem(name: "term", value: "chat"),
            URLQueryItem(name: "limit", value: "\(size)"),
            URLQueryItem(name: "offset", value: "\(index * size)"),
        ]
        var response: AppDataResponse?
        cancellationToken = AppDataAPI.request(.search, queryItems)
            .sink { [weak self] completion in
                switch completion {
                case .finished:
                    self?.handleResponse(response, isRefresh)
                case .failure(let error):
                    print(error)
                }
            } receiveValue: {
                response = $0
            }
    }
    
    private func handleResponse(_ response: AppDataResponse?, _ isRefresh: Bool) {
        guard let response = response else { return }
        // 获取数据，并转化为ViewModel
        let results = response.results.map({ return AppDataRowViewModel(appData: $0) })
        // 如果数据不为空，页码+1
        if response.resultCount != 0 {
            self.index += 1
        }
        // 根据isRefresh判断是更新还是刷新
        if isRefresh {
            self.appDataList = results
        } else {
            self.appDataList += results
        }
        // 数据为空表示不再有新数据了
        self.isNoMore = response.resultCount == 0
        // 重置状态
        self.isRefreshing = false
        self.isLoadingMore = false
        // 标记已加载过
        self.isDataLoaded = true
    }
    
}
