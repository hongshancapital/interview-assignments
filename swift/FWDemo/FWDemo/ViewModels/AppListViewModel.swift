//
//  AppListViewModel.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation
import Combine

/// AppInfoListView 业务模型
class AppListViewModel : ObservableObject {
    
    @Published var datalist = [AppInfo]()       /// 列表数据源
    @Published var inRefreshing = false         /// 下拉刷新状态标志
    @Published var inLoadingMore = false        /// 上拉加载更多标志
    @Published var isLoadCompletion = false     /// 数据全部加载完毕标志
    
    private var pageIndex = 1
    private let pageSize = 20
    
    var cancellable : AnyCancellable?
    
    /// 下拉刷新数据
    func refresh() {
        if inRefreshing || inLoadingMore {
            return
        }
        
        self.fetchListData(1, pageSize)
    }
    
    /// 上拉加载更多数据
    func loadMore() {
        if inRefreshing || inLoadingMore {
            return
        }
        
        self.fetchListData(pageIndex+1, pageSize)
    }
    
    private func fetchListData(_ pageIndex : Int, _ pageSize : Int) {
        cancellable = NetworkManager.shared.sendRequest(requestType: AppInfoApis.getAppInfolist(pageIndex, pageSize))
        .tryMap{ $0.data }
        .decode(type: AppApiResponse.self, decoder: JSONDecoder())
        .tryMap { response in
            return response
                .results
                .suffix(from: (pageIndex-1) * pageSize)
                .prefix(pageSize)
        }
        .receive(on: RunLoop.main)
        .sink(receiveCompletion: {completion in
            switch completion {
                case .finished:
                    print("==== applist : fetch success")
                case .failure(let e):
                    print("==== applist : fetch error : \(e)")
            }
            
        }, receiveValue: { datalist in
            print("==== applist : fetch data : \(datalist) \n === \(datalist.count)")
            self.pageIndex = pageIndex
            self.inRefreshing = false
            self.inLoadingMore = false
            self.isLoadCompletion = datalist.count < pageSize
            if pageIndex == 1 {
                self.datalist = [AppInfo](datalist)
            }else {
                self.datalist.append(contentsOf: datalist)
            }
        })
    }
}
