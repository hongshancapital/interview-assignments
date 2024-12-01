//
//  ListViewModel.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import Foundation
import Combine
import SwiftUI

class ListViewModel: ObservableObject {
    
    @Published var datas: [ListCell] = []
    @Published var isFavorites: [Bool] = []
    @Published var isLoading: LoadingState = .Loading
    @Published var errorMessage: String?
    
    let maxRow: Int = 10

    var loadMoreSubject = CurrentValueSubject<Void, FetchListError>(())
    var refreshSubject = PassthroughSubject<Void, Never>()
    var updateLikeSubject = PassthroughSubject<(Int, Bool), Never>()
    var cancellables = Set<AnyCancellable>()
    
    var dataService: ApiProtocol
    
    init(dataService: ApiProtocol = ApiService()) {
        self.dataService = dataService
        
        addLoadListSubscription()
        self.isFavorites = dataService.fetchFavoriteData()
        addUpdateLikeSubscription()
        addRefreshListSubscription()
    }
    
    // MARK: - 是否需要加载更多List内容
    func isLoadMore(cell: ListCell) {
        if let last = datas.last {
            if cell.trackId == last.trackId {
                loadMoreSubject.send()
            }
        }
    }
}

extension ListViewModel {
    
    /// 刷新列表
    /// - 下拉时执行，重置`isLoading`和`datas`等数据
    ///   重新发起`addLoadListSubscription`流程
    private func addRefreshListSubscription() {
        refreshSubject
            .debounce(for: .seconds(1), scheduler: RunLoop.main)
            .sink { [weak self] _ in
                self?.errorMessage = nil
                self?.isLoading = .Loading
                self?.datas.removeAll()
                self?.addLoadListSubscription()
            }
            .store(in: &cancellables)
    }

    /// 设置列表项`收藏`状态
    /// - 1. Subject的Output为序号和状态的元祖
    ///   2. 用map与switchToLatest防止过多的点击操作
    ///   3. 接收到的Output为所有列表项收藏状态的数组,更新`isFavorites`数组
    private func addUpdateLikeSubscription() {
        updateLikeSubject
            .map { self.dataService.updateListData(id: $0.0, isFavorite: $0.1) }
            .switchToLatest()
            .receive(on: RunLoop.main)
            .sink { [weak self] value in
                if !value.isEmpty {
                    withAnimation(.spring(response: 0.4)) {
                        self?.isFavorites = value
                    }
                }
            }
            .store(in: &cancellables)
    }
    
    /// 获取列表数据
    /// - 用数据服务的fetchListData获取列表数据，此Publisher的Output为[ListCell]类型
    ///   转换其Output为序列类型，可以依次接收单个数据。设置接收的数量为maxRow (10)
    ///   和loadMoreSubject组合（用户上拉到当前列表底部时发送），达到每次获取maxRow条数据的效果
    /// - isLoading状态判断：
    ///   - sink收到.finished状态表示所有数据接收完毕，应显示`No more data.`
    ///   - 其余情况显示`Load...`,在handleEvents中处理
    private func addLoadListSubscription() {
        // 获取列表数据 + 滚动到底部
        self.dataService.fetchListData()
            .flatMap { $0.publisher }
            .collect(maxRow)
            .zip(self.loadMoreSubject)
            .delay(for: .seconds(1), scheduler: DispatchQueue.global())
            .receive(on: RunLoop.main)
            .handleEvents(
                receiveOutput: { [weak self] _ in
                    self?.isLoading = .LoadMore
                })
            .sink { [weak self] completion in
                switch completion {
                case .finished:
                    self?.isLoading = .LoadComplete
                    break
                case .failure(let error):
                    print("[Error]: \(error.description)")
                    DispatchQueue.main.async {
                        self?.errorMessage = error.description
                    }
                    break
                }
            } receiveValue: { [weak self] value in
                if value.0.count > 0 {
                    self?.datas.append(contentsOf: value.0)
                }
            }
            .store(in: &cancellables)
    }
    
}
