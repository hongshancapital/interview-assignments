//
//  AppListViewModel.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//  处理网络请求

import Foundation
import Combine

let loadDataURL     = "https://itunes.apple.com/search?entity=software&limit=25&term=chat"
let loadMoreDataURL = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"

final class AppListViewModel: ObservableObject {
    
    @Published var appInfos : [AppInfo] = []
    @Published var allDataIsLoaded : Bool = false
    @Published var favoriteAppIds : Set<Int> = []
    @Published var error : String?
    
    init() {
        loadData()
    }
    
    // 下拉刷新
    func loadData() {
        self.error = nil
        self.allDataIsLoaded = false
        
        Network.get(from: loadDataURL) { (response: AppResult) in
            self.appInfos = response.results
        } errorClosure: { errorMsg in
            self.error = errorMsg
        }
    }
    
    // 加载更多
    func loadMoreData() {
        if allDataIsLoaded {
            return
        }
        Network.get(from: loadMoreDataURL) { (response: AppResult) in
            self.appInfos = response.results
            self.allDataIsLoaded = true
        } errorClosure: { errorMsg in
            self.error = errorMsg
        }
    }
}
