//
// Created by Jeffrey Wei on 2022/6/29.
//

import Foundation
import SwiftUI
import Combine

class ListViewStore: ObservableObject {
    @Published var dataSource: [DemoModel] = []
    @Published var hasMore = true
    private let pageSize = 20
    private var currentPage = 0
    private var getListCancellable: AnyCancellable?
    private var doCollectedCancellable: AnyCancellable?

    @Sendable func refresh() {
        request(isRefresh: true)
    }

    @Sendable func loadMore() {
        request(isRefresh: false)
    }

    @Sendable private func request(isRefresh: Bool) {
        let pageNum = isRefresh ? 1 : currentPage + 1
        getListCancellable = Api.getDemoList(pageSize: pageSize, pageNum: pageNum)
                .sinkResultData(
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
                                var arr = weakSelf.dataSource
                                arr.append(contentsOf: list)
                                weakSelf.dataSource = arr
                            }
                            let hasMore = list.count == weakSelf.pageSize
                            weakSelf.hasMore = hasMore
                        })
    }

    func doCollected(index: Int) {
        var model = dataSource[index]
        let isCollected = !model.isCollected
        doCollectedCancellable = Api.doCollected(
                id: model.id,
                isCollected: isCollected).sinkResultData(
                dataCls: Bool.self,
                receiveCompletion: { completion in

                },
                receiveValue: { [weak self] in
                    guard  let weakSelf = self, let isCollected = $0 else { return }
                    model.isCollected = isCollected
                    weakSelf.dataSource[index] = model
                })
    }
}
