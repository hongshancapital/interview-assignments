//
//  HomePageViewModel.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation
import Combine

extension HomePageView {
    class ViewModel: ObservableObject {
        /// 请求服务
        var service: DemoRestfulService
        
        /// 持有订阅
        private var subscribers = Set<AnyCancellable>()
        
        /// 列表数据
        @Published var applications: Loadable<[Application]> = .notRequested
        
        /// 收藏的我条目
        @Published var favourites: [Application] = []
        
        /// 是否正在加载更多
        @Published var isMoreDataLoading = false
        
        /// 一页数量
        private let pageSize = 10
        
        /// 总页数
        private let maxPage = 2
        
        /// 当前页
        private var currentPage = 0
        
        
        /// 是否是最后一页
        var isLastPage: Bool {
            currentPage >= maxPage
        }
        
        init(service: DemoRestfulService = DemoRealRestfulService()) {
            self.service = service
            queryList()
        }
        
        
        /// 查询列表数据
        func queryList() {
            applications = .isLoading(last: applications.value)
            
            service.homePageList(limit: (currentPage + 1) * pageSize).weakSinkOn(self) {
                if case let .failure(error) = $1 {
                    $0.applications = .failed(error)
                }
            } receiveValue: { (self, results) in
                self.applications = .loaded(results)
                self.currentPage += 1
            }.store(in: &subscribers)
            
        }
        
        
        func loadMore() {
            service.homePageList(limit: (currentPage + 1) * pageSize).weakSinkOn(self) {
                if case let .failure(error) = $1 {
                    $0.applications = .failed(error)
                }
            } receiveValue: { (self, results) in
                DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(2)) {
                    self.isMoreDataLoading = false
                    self.applications = .loaded(results)
                    self.currentPage += 1
                }
            }.store(in: &subscribers)
        }
        
        /// 下拉刷新事件
        @MainActor
        func refreshList() async {
            do {
                self.currentPage = 0
                let results = try await service.homePageList(limit: (currentPage + 1) * pageSize)
                self.currentPage += 1
                
                /// 动画太快消失，延迟1秒
                try await Task.sleep(nanoseconds: 1_000_000_000)
                
                self.applications = .loaded(results)
            } catch let error {
                self.applications = .failed(error)
            }
        }
        
        /// 收藏事件
        func collectTapAction(application: Application) {
            if let index = favourites.firstIndex(of: application) {
                favourites.remove(at: index)
            } else {
                favourites.append(application)
            }
        }
        
        /// 是否是收藏过的应用
        func isFavourited(for application: Application) -> Bool {
            favourites.contains(application)
        }
    }
}
