//
// Created by Jeffrey Wei on 2022/6/29.
//

import Foundation
import SwiftUI
import Combine

class ListViewStore: ObservableObject {
    @Published var dataSource: [DemoModel] = []
    private var getListCancellable: AnyCancellable?

    @Sendable func refresh() {
        getListCancellable = Api.getDemoList(pageSize: 20, pageNum: 1)
                .sinkResultData(
                        dataCls: [DemoModel].self,
                        receiveCompletion: {
                            completion in
                        },
                        receiveValue: { [weak self]value in
                            self?.dataSource = value ?? []
                        })
    }
}
