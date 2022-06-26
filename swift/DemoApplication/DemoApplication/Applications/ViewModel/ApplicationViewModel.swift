//
//  ApplicationViewModel.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import SwiftUI
import Combine

enum LoadStatus {
    case initializing //首次进入
    case loading   //加载中
    case empty     //因网络错误等原因导致未请求到数据
    case loaded    //单次网络请求完成
    case completed //所有网络数据均加载完成
}

final class ApplicationViewModel: ObservableObject {
    
    @Published var items  = [ApplicationItem]()
    
    public var status = LoadStatus.initializing
    
    private var pageIndex = 0
    private let pageSize  = 20
    private var maxLimit  = 50
    
    private let service   =  NetworkRequestService.shared
    private var cancellable: AnyCancellable?
    
    init(_ maxLimit: Int = 50) {
        self.maxLimit = maxLimit
    }
    
    func refresh() {
        guard status != .loading else {
            return
        }
        status = .initializing
        pageIndex = 0

        loadData(pageIndex, pageSize: pageSize)
    }
    
    func loadNextPage() {
        guard status != .loading &&
              status != .completed else {
            return
        }

        pageIndex += 1
        loadData(pageIndex, pageSize: pageSize)
        
    }
    
    
    private func loadData(_ pageIndex: Int, pageSize: Int) {
        status = .loading
        cancellable = service
            .request(request())
            .map {
                $0.suffix(from: pageIndex * pageSize)
            }
            .receive(on: RunLoop.main)
            .sink { items in
                if pageIndex == 0 {
                    self.items = Array(items)
                } else {
                    self.items.append(contentsOf: items)
                }
                
                self.status = .loaded
                if self.items.count == 0 {
                    self.status = .empty
                }
                
                if self.items.count >= self.maxLimit {
                    self.status = .completed
                }
            }
    }
    
    private func request() -> URLRequest {
        let limit     = min(pageSize * (pageIndex + 1), maxLimit)
        let urlString = """
        https://itunes.apple.com/search?\
        entity=software&limit=\(limit)&term=chat
        """
        
        let url = URL(string: urlString)!
        return URLRequest(url: url, timeoutInterval: 20)
    }
}


