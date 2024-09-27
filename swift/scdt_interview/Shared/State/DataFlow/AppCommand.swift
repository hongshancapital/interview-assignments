//
//  AppCommand.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Combine
import Foundation
import SwiftUI
import UIKit

protocol AppCommand {
    func execute(in store: Store)
}

struct LoadAppListCommand: AppCommand {
    enum ListType {
        case loadData
        case headerRefresh
        case footerRefresh
    }

    let pageSize: Int
    let pageIndex: Int
    let type: ListType

    init(pageSize: Int, pageIndex: Int, type: ListType) {
        self.pageSize = pageSize
        self.pageIndex = pageIndex
        self.type = type
    }

    func execute(in store: Store) {
        let token = SubscriptionToken()
        listPublisher.sink(
            receiveCompletion: { complete in
                if case .failure(let error) = complete {
                    switch type {
                    case .loadData:
                        store.dispatch(.loadAppListDone(result: .failure(error)))
                    case .headerRefresh:
                        store.dispatch(.loadAppListHeaderDone(result: .failure(error)))
                    case .footerRefresh:
                        store.dispatch(.loadAppListFooterDone(result: .failure(error)))
                    }
                }
                token.unseal()
            }, receiveValue: { value in
                switch type {
                case .loadData:
                    store.dispatch(.loadAppListDone(result: .success(value)))

                case .headerRefresh:
                    store.dispatch(.loadAppListHeaderDone(result: .success(value)))

                case .footerRefresh:
                    store.dispatch(.loadAppListFooterDone(result: .success(value)))
                }
            }
        )
        .seal(in: token)
    }

    var listPublisher: AnyPublisher<[AppListViewModel], AppError> {
        Future { promise in
            // Delay the task by 2 second:
            DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
                do {
                    let listData = AppListViewModel.pageSamples(size: pageSize, index: pageIndex)
                    promise(.success(listData))
                }
                catch {
                    promise(.failure(.fileError))
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}

class SubscriptionToken {
    var cancellable: AnyCancellable?
    func unseal() { cancellable = nil }
}

extension AnyCancellable {
    func seal(in token: SubscriptionToken) {
        token.cancellable = self
    }
}
