//
//  HomeData.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI
import Combine

final class HomeData: ObservableObject {
    var pageSize: Int = 20
    var pageNo: Int = 1
    
    @Published var listArray = [ListModel]()
    @Published var hasMoreData: Bool = false
    
    init() {
        getListData()
    }
    
    func getListData() {
        listArray.removeAll()
        pageNo = 1
        
        let currentPageSize = listData.count > pageSize ? pageSize : listData.count
        let start = listData.index(listData.startIndex, offsetBy: 0)
        let end = listData.index(start, offsetBy: currentPageSize)
        listArray.append(contentsOf: listData[start..<end])
        
        hasMoreData = listArray.count == listData.count ? false : true
    }
    
    func getMoreData() {
        guard hasMoreData else {
            return
        }
        let remainingCount = listData.count - listArray.count
        let currentPageSize = remainingCount > pageSize ? pageSize : remainingCount
        
        let start = listData.index(listData.startIndex, offsetBy: pageNo * pageSize)
        let end = listData.index(start, offsetBy: currentPageSize)
        listArray.append(contentsOf: listData[start..<end])
        
        pageNo += 1
        hasMoreData = listArray.count == listData.count ? false : true
    }
}
