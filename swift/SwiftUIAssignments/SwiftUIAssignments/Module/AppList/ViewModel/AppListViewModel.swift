//
//  AppListViewModel.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import Foundation
import Combine

@MainActor
class AppListViewModel: ObservableObject {
    /// app model for List
    @Published private(set) var appModels: [AppModel]

    /// if `isInitial` is true,  show initial loading view
    @Published private(set) var isInitial: Bool
    /// loading more date
    @Published private(set) var isLoading: Bool
    @Published private(set) var noMoreData: Bool

    /// alter for network.
    @Published var isShowAlert: Bool
    private(set) var alertMessage: String

    ///  mark liked app ids
    var likedAppIds = [Int]()

    // MARK: - private property
    /// current page for list
    private var currentPage = 1
    /// totoal apps
    private var total = 0
    /// page size
    private var pageSize = 20

    private var cancelable = Set<AnyCancellable>()
    
    // MARK: - system method
    init() {
        appModels = Array()
        isInitial = true
        isLoading = false
        isShowAlert  = false
        noMoreData   = false
        alertMessage = ""

        startMonitoringNetwork()
    }
}


extension AppListViewModel {

    /// footer State
    var footerState: RefreshFooterView.State {
        if isLoading {
            return .loading
        } else if noMoreData {
            return .noMoreData
        } else {
            return .idel
        }
    }

    /// app list is empty
    var isAppEmpty: Bool {
        appModels.isEmpty
    }

    /// init app list
    func initAppList() async {
        isInitial = true
        await fetchAppList(isRefresh: true)
        
        // init completion
        isInitial = false
    }

    /// refresh app list when pull to refresh
    func refresh() async {
        await fetchAppList(isRefresh: true)

        // reset no more date
        noMoreData = currentPage * pageSize >= total
    }

    /// load more
    func loadMore() async {
        if noMoreData { return }

        isLoading  = true
        await fetchAppList(isRefresh: false)

        // update footer state
        isLoading  = false
        noMoreData = currentPage * pageSize >= total
    }

    /// like some of app
    /// - Parameter app: app model
    func like(_ app: AppModel) {
        let index = appModels.firstIndex { $0.id == app.id }
        guard let index else { return }
        appModels[index].isLike.toggle()

        // update liked app ids
        if appModels[index].isLike {
            likedAppIds.append(app.id)
        } else {
            likedAppIds.removeAll { $0 == app.id }
        }
    }
}

// MARK: - private
private extension AppListViewModel {

    func startMonitoringNetwork() {
        NetworkMonitor.shared.publisher
            .debounce(for: 1, scheduler: RunLoop.main)
            .sink { status in
                switch status {
                case .connected:
                    self.isShowAlert = false
                    self.alertMessage = ""
                case .unConnected:
                    self.isShowAlert  = true
                    self.alertMessage = "Network unConnected"
                case .requiresConnection:
                    self.isShowAlert  = true
                    self.alertMessage = "Network require connection"
                }
            }
            .store(in: &cancelable)
    }

    func fetchAppList(isRefresh: Bool) async {
        var page: Int
        if isRefresh {
            page = 1
        } else {
            page = currentPage + 1
        }
        let endpoint = APIService.Endpoint.appList(page: page, count: pageSize)
        let result = await APIService.shared.mockRequest(endpoint)
        switch result {
        case let .success(response):
            currentPage = response.currentPage
            total = response.total

            if isRefresh {
                appModels.removeAll()
            }

            // sync liked app model
            var syncAppModels = [AppModel]()
            for model in response.appModels {
                var syncAppModel = model
                syncAppModel.isLike = likedAppIds.contains(syncAppModel.id)
                syncAppModels.append(syncAppModel)
            }
            appModels.append(contentsOf: syncAppModels)

        case let .failure(error):
            print("api reqeust error: \(error)")
            isShowAlert = true
            alertMessage = error.localizedDescription
        }
    }
}

// MARK: - Testing
extension AppListViewModel {

    /// set appModels for testing given
    func setAppModelsForTesting(_ appModels: [AppModel]) {
        self.appModels = appModels
    }

    /// set isInitail for testing given
    func setIsInitialForTesting(_ isInitail: Bool) {
        self.isInitial = isInitail
    }

    /// set noMoreData for testing given
    func setNoMoreDataForTesting(_ noMoreData: Bool) {
        self.noMoreData = noMoreData
    }
}
