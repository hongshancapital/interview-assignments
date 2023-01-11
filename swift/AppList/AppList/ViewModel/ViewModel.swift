//
//  ViewModel.swift
//  AppList
//
//  Created by wozyao on 2022/10/25.
//

import SwiftUI

class ViewModel: ObservableObject {
    
    /// app数组
    @Published var appList: [AppModel] = [AppModel]()
    /// 请求页码
    @Published var pageNum: Int = 1
    /// 是否正在下拉刷新，默认否
    @Published var headerRefreshing = false
    /// 是否正在上拉加载，默认否
    @Published var footerRefreshing = false
    /// 是否无更多数据，默认否
    @Published var noMore = false
    
    /// 加载最新的App数据
    func loadNewData() {
        self.pageNum = 1
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.appList = self.loadData()
            self.headerRefreshing = false
        }
    }
    
    /// 加载更多的App数据
    func loadMoreData() {
        pageNum += 1
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            let temp = self.loadData()
            self.appList.append(contentsOf: temp)
            self.footerRefreshing = false
            self.noMore = temp.count < Constants.PAGE_SIZE ? true : false
        }
    }
    
    /// 加载接口数据
    /// - Returns: 返回App集合
    private func loadData() -> [AppModel] {
        
        let startIndex = (pageNum - 1) * Constants.PAGE_SIZE
        let endIndex = pageNum * Constants.PAGE_SIZE - 1
        let maxIndex = dataSource.count - 1
        
        if startIndex > maxIndex { // 若起始索引大于最大索引，则返回空数组
            return []
        } else if maxIndex < endIndex { // 若最大索引小于结束索引，则结束索引为最大索引
            let slice = dataSource[startIndex ... maxIndex]
            return Array(slice)
        } else {
            let slice = dataSource[startIndex ... endIndex]
            return Array(slice)
        }
    }
}
