//
//  HomeViewModel.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Combine
import Foundation

protocol HomeViewModelCommonProtocol {}
class HomeViewModel: ObservableObject, HomeViewModelCommonProtocol {
    private var cancellables = Set<AnyCancellable>()
    let apiService: AppInfoListServiceProtocol
    @Published var results: [AppInfo] = []
    @Published var state: ViewRequestResultState<AppInfo, APIError> = .loading
    private var page: Int = 0
    @Published var noMoreData: Bool = false
    @UserDefault(key: "favoriteAppIds", defaultValue: [])
    var collectAppTrackIds: [Int]

    init(apiService: AppInfoListServiceProtocol) {
        self.apiService = apiService
    }

    func reload() {
        page = 0
        results = []
        fetchApp()
    }

    func loadNext() async throws {
        try await Task.sleep(until: .now + .seconds(0.5), clock: .continuous)
        fetchApp()
    }

    func addCollection(item: AppInfo) {
        let id = item.trackId
        if let index = collectAppTrackIds.firstIndex(where: { $0 == id }) {
            collectAppTrackIds.remove(at: index)
        } else {
            collectAppTrackIds.append(id)
        }

        if let index = results.firstIndex(where: { $0.trackId == id }) {
            results[index].isCollected.toggle()
        }
    }

    private func fetchApp() {
        let cancellable = apiService.requestApps(from: AppInfoListEndpoint.appInfoList(page: page))
            .receive(on: DispatchQueue.main).sink { res in
                switch res {
                case .finished:
                    self.state = .success(content: self.results)
                    if !self.noMoreData {
                        self.page += 1
                    }
                case let .failure(error):
                    self.state = .failed(error: error)
                }
            } receiveValue: { (data: AppInfoResponse) in
                if let res = data.results {
                    self.results += res
                    self.noMoreData = false
                } else {
                    self.noMoreData = true
                }
            }
        cancellables.insert(cancellable)
    }
}
