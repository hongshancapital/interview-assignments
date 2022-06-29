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
                                weakSelf.dataSource.append(contentsOf: list)
                            }
                            let hasMore = list.count == weakSelf.pageSize
                            weakSelf.hasMore = hasMore
                        })
    }
}
