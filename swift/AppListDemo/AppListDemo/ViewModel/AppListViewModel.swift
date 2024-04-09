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
            DispatchQueue.main.async {
                self.isFirstLoading = false
                self.errorString = "\(error)"
            }
            return []
        }
    }()
    private let numberOnePage = 10
    private var currentPage = 0
    private let serialQueue = DispatchQueue(label: "Serial")
    
    @discardableResult
    private func refreshData(_ isRefreshing: Bool = false) async -> Int {
        await withUnsafeContinuation { continuation in
            serialQueue.async { [weak self] in
                guard let self = self, !self.mockData.isEmpty else {
                    continuation.resume(returning: -1)
                    return
                }
                
                if isRefreshing {
                    DispatchQueue.main.async {
                        self.isFirstLoading = true
                        self.isNoMoreData = false
                        self.dataSource.removeAll()
                    }
                }
                
                sleep(1) // 模拟请求耗时
                
                let start = self.currentPage * self.numberOnePage
                var offset = self.numberOnePage
                if self.mockData.count <= start + self.numberOnePage {
                    offset = self.mockData.count - start
                    DispatchQueue.main.async {
                        self.isNoMoreData = true
                    }
                }
                
                let arr = Array(self.mockData[start..<start + offset])
                
                DispatchQueue.main.async {
                    self.isFirstLoading = false
                    self.dataSource.append(contentsOf: arr)
                    continuation.resume(returning: 0)
                }
            }
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

