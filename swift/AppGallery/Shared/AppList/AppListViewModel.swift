//
//  AppListViewModel.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

final class AppListViewModel: ObservableObject, ListObject {
    
    typealias Output = ChatApp
    
    @Published var isFetching: Bool = false
    
    @Published var loadState: LoadableState<[ChatApp]> = .idle
    
    private(set) var appListService: AppListService
    
    private(set) var pageSize: Int
    
    init(service: AppListService, pageSize: Int) {
        self.appListService = service
        self.pageSize = pageSize
    }
    
    @MainActor
    func load() async {
        loadState = .loading
        await fetchApps(limit: pageSize)
    }
    
    func refresh() async {
        await fetchApps(limit: pageSize)
    }
    
    func loadMore() async {
        switch loadState {
        case .loaded(let items, let hasMore):
            guard hasMore else { return }
            await fetchApps(limit: items.count + pageSize)
        case .error(_):
            await refresh()
        case .idle, .loading:
            return
        }
    }
    
    func toggleMark(for app: ChatApp) {
        if app.isMarked {
            LocalMarkService.shared.update(by: app.id, marked: false)
        } else {
            LocalMarkService.shared.update(by: app.id, marked: true)
        }
        objectWillChange.send()
    }
}

extension AppListViewModel {
    
    @MainActor
    private func fetchApps(limit: Int) async {
        guard !isFetching else { return }
        isFetching = true
        do {
            let apps = try await appListService.fetchApps(limit: limit)
            loadState = .loaded(content: apps, couldLoadMore: checkIfHasMore(itemsCount: apps.count, limit: limit))
        } catch {
            // deal with simply
            loadState = .error(error)
        }
        isFetching = false
    }
    
    private func checkIfHasMore(itemsCount: Int, limit: Int) -> Bool {
        return itemsCount >= limit
    }
    
}
