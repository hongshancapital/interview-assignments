//
//  AppCommand.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation
import Combine


protocol AppCommand {
    func execute(in store: AppStore)
}

struct RefreshAppListCommand: AppCommand {
    func execute(in store: AppStore) {
        LoadAppListRequest.refresh()
            .sink(
                receiveCompletion: { complete in
                    debugPrint("🦁️received refresh completion",
                          String(describing: complete))
                    if case .failure(let error) = complete {
                        store.dispatch(.refreshDone(result: .failure(error)))
                    }
                }, receiveValue: { value in
                    store.dispatch(.refreshDone(result: .success(value)))
                }
            )
            .add(to: store.disposeBag)
    }
}

struct LoadMoreAppListCommand: AppCommand {
    func execute(in store: AppStore) {
        LoadAppListRequest.loadMore(pageIndex: store.appState.appList.pageIndex, pageAppsNum: store.appState.appList.pageAppsNum)
            .sink(
                receiveCompletion: { complete in
                    debugPrint("🦁️received loadMore completion",
                          String(describing: complete))
                    if case .failure(let error) = complete {
                        store.dispatch(.loadMoreDone(result: .failure(error)))
                    }
                }, receiveValue: { value in
                    store.dispatch(.loadMoreDone(result: .success(value)))
                }
            )
            .add(to: store.disposeBag)
    }
}

struct ClearCacheCommand: AppCommand {
    func execute(in store: AppStore) {
        // clean cache
    }
}
