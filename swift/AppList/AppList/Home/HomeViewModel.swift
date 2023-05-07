//
//  HomeViewModel.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/1.
//

import Foundation
import SwiftUI

class HomeViewModel: ObservableObject {
    
    private let APP_LIMIT_COUNT: Int = 20
    private var seq: Int = 0
    
    /// 获取原始数据
    private lazy var appSourceList: AppResultModel? = {
        return try? FileUtils.loadJson(fileName: "Apps")
    }()
    
    @Published var appList: [AppModel] = []
    @Published var isLoading: Bool = true
    
    @State var listState = ListState()
    @State var isHeaderRefreshing: Bool = false
    @State var isFooterRefreshing: Bool = false
    
    /// 异步获取应用列表
    func fetchSubsequentAppList() {
        defer {
            isFooterRefreshing = false
            isLoading = false
        }
        guard let sourceList = self.appSourceList?.results else {
            // 抛出错误
            return
        }
        
        if seq < 0 {
            // appList保持不变
            return
        }
        
        // 每次请求最多20个应用.
        if sourceList.count - seq < APP_LIMIT_COUNT  {
            let result = sourceList[seq...]
            self.appList.append(contentsOf: result)
            seq = -1
            listState.setNoMore(true)
        } else {
            let lastSeq = seq+APP_LIMIT_COUNT-1
            let result = sourceList[seq...lastSeq]
            self.appList.append(contentsOf: result)
            seq += APP_LIMIT_COUNT
            listState.setNoMore(false)
        }
    }
    
    func fetchPreviousAppList() {
        isLoading = false
        isHeaderRefreshing = false
    }
}
