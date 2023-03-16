//
//  AppListViewModel.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import Foundation

@MainActor
final class AppListViewModel: ObservableObject {
    var requestDataService: RequestDataService = MockDataService.shared
    
    @Published var appModels: [AppModel] = []
    
    @Published private(set) var isFirstLoading = true
    @Published private(set) var isRefreshing = false
    @Published private(set) var loadingMoreStatus: LoadingMoreStatus = .initialized
    
    @Published private(set) var hasMoreData = true
    
    @Published private(set) var isShow = false
    @Published private(set) var toastMessage = ""
    
    private(set) var currentPage: Int = 0
    private(set) var pageCount: Int = 10
    
    init(pageCount: Int) {
        self.pageCount = pageCount
    }
    
    private func loadFirstPage() async {
        do {
            let response = try await requestDataService.fetchAppList(atPage: 0, pageCount: pageCount)
            if response.code == .success {
                currentPage = 0
                hasMoreData = (response.appModels.count == pageCount)
                appModels.removeAll()
                appModels.append(contentsOf: response.appModels)
            } else {
                await showToast("Request failed")
            }
        } catch {
            await showToast("Request failed")
            print(error)
        }
    }
    
    private func loadNextPage() async {
        do {
            let response = try await requestDataService.fetchAppList(atPage: currentPage + 1, pageCount: pageCount)
            if response.code == .success {
                currentPage = currentPage + 1
                hasMoreData = (response.appModels.count == pageCount)
                appModels.append(contentsOf: response.appModels)
                loadingMoreStatus = .success
            } else {
                loadingMoreStatus = .fail
                await showToast("Request failed")
            }
        } catch {
            loadingMoreStatus = .fail
            await showToast("Request failed")
            print(error)
        }
    }
    
    private func toggleFavouriteAppInCache(_ appModel: AppModel) {
        if let idx = appModels.firstIndex(where: { $0.id == appModel.id }) {
            appModels[idx].isFavourite = !appModels[idx].isFavourite
        }
    }
    
    private func showToast(_ message: String) async {
        do {
            isShow = true
            toastMessage = message
            
            try await Task.sleep(for: Duration.seconds(2))
            
            isShow = false
            toastMessage = ""
        } catch {
            print(error)
        }
    }
}

extension AppListViewModel {
    enum LoadingMoreStatus: Int {
        case initialized
        case loading
        case success
        case fail
    }
}

// MARK: - Public Methods
extension AppListViewModel {
    func firstLoad() async {
        isFirstLoading = true
        await loadFirstPage()
        isFirstLoading = false
    }
    
    func refresh() async {
        if isRefreshing {
            return
        }
        isRefreshing = true
        await loadFirstPage()
        isRefreshing = false
    }
    
    func loadMore() async {
        if loadingMoreStatus == .loading {
            return
        }
        loadingMoreStatus = .loading
        await loadNextPage()
    }
    
    func toggleFavouriteApp(_ appModel: AppModel) async {
        do {
            toggleFavouriteAppInCache(appModel)
            let response = try await requestDataService.toggleFavouriteApp(appModel)
            if response.code == .fail {
                toggleFavouriteAppInCache(appModel)
                await showToast("Request failed")
            }
        } catch {
            await showToast("Request failed")
            print(error)
        }
    }
}
