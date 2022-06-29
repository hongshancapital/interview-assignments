//
// Created by Jeffrey Wei on 2022/6/29.
//

import Foundation
import SwiftUI
import Combine

class ListViewStore: ObservableObject {
    // 列表数据源
    @Published var dataSource: [DemoModel] = []
    // 是否有更多数据
    @Published var hasMore = true
    // 查询数据每页的大小
    private let pageSize = 20
    // 当前页
    private var currentPage = 0
    // 列表请求的cancellable
    private var getListCancellable: AnyCancellable?
    // 收藏请求的cancellable
    private var doCollectedCancellable: AnyCancellable?
    // 是否正在点击收藏,简易的处理下防重复请求
    private var isRequestDoCollected = false

    // 刷新函数,外部只关联函数名即可
    @Sendable func refresh() {
        request(isRefresh: true)
    }

    // 加载更多函数,外部只关联函数名即可
    @Sendable func loadMore() {
        request(isRefresh: false)
    }

    // 刷新和加载更多的具体实现函数
    @Sendable private func request(isRefresh: Bool) {
        let pageNum = isRefresh ? 1 : currentPage + 1
        getListCancellable = Api.getDemoList(pageSize: pageSize, pageNum: pageNum)
                .sinkResponseData(
                        dataCls: [DemoModel].self,
                        receiveCompletion: { [weak self] in
                            guard  let weakSelf = self else { return }
                            switch $0 {
                            case .finished:
                                weakSelf.currentPage = pageNum
                            case .failure:
                                break
                            }
                        },
                        receiveValue: { [weak self]value in
                            guard let weakSelf = self else { return }
                            let list = value ?? []
                            switch isRefresh {
                            case true:
                                weakSelf.dataSource = list
                            case false:
                                weakSelf.dataSource.append(contentsOf: list)
                            }
                            weakSelf.hasMore = list.count == weakSelf.pageSize
                        })
    }

    func doCollected(index: Int) {
        guard isRequestDoCollected == false else { return }
        isRequestDoCollected = true
        var model = dataSource[index]
        let isCollected = !model.isCollected
        doCollectedCancellable = Api.doCollected(
                id: model.id,
                isCollected: isCollected).sinkResponseData(
                dataCls: Bool.self,
                receiveCompletion: { [weak self] completion in
                    self?.isRequestDoCollected = false
                },
                receiveValue: { [weak self] in
                    guard  let weakSelf = self, let isCollected = $0 else { return }
                    model.isCollected = isCollected
                    weakSelf.dataSource[index] = model
                })
    }
}
