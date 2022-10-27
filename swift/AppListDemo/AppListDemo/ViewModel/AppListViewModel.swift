//
//  AppListViewModel.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/23.
//

import Foundation

protocol LoadMoreable: ObservableObject {
    var isNoMoreData: Bool { get set }
    func loadMore() async
}

class AppListViewModel: ObservableObject, LoadMoreable {
    
    @Published private(set) var dataSource: [AppInfo] = []
    @Published var isFirstLoading = true
    @Published var isNoMoreData: Bool = false
    @Published var errorString: String?
    private lazy var mockData: [AppInfo] = {
        do {
            return try MockData.list()
        } catch let error {
            errorString = "\(error)"
            return []
        }
    }()
    private let numberOnePage = 10
    private var currentPage = 0
    
    private func refreshData(_ isRefreshing: Bool = false) async {
        if isRefreshing {
            isFirstLoading = true
            isNoMoreData = false
            dataSource.removeAll()
        }
        
        let start = currentPage * numberOnePage
        var offset = numberOnePage
        if mockData.count <= start + numberOnePage {
            offset = mockData.count - start
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                self.isNoMoreData = true
            }
        }
        
        let arr = Array(mockData[start..<start + offset])
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.isFirstLoading = false
            self.dataSource.append(contentsOf: arr)
        }
    }
    
    func refreshing() async {
        currentPage = 0
        await refreshData(true)
    }
    
    func loadMore() async {
        if isNoMoreData {
            return
        }
        currentPage += 1
        await refreshData()
    }
}

