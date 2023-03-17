//
//  StoreListViewModel.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/1.
//

import Foundation

fileprivate enum Constants {
    enum Refresh {
        static let normal = "Pull up to load more."
        static let refreshing = "Loading ..."
        static let nomoredata = "No more data."
    }
}

enum RefreshState {
    case normal(String)
    case refreshing(String)
    case nomoredata(String)
}

class StoreListViewModel: ObservableObject {
    @Published var storeModel = StoreModel()
    @Published var refreshState: RefreshState = .normal(Constants.Refresh.normal)
    @Published var loadFinished = false
    
    var apps: [AppModel] {
        storeModel.apps
    }
    
    init() {
        firstFetchData()
    }
    
    func like(_ id: Int) {
        storeModel.like(id)
    }
    
    func addApp(_ apps: [AppModel]) {
        storeModel.addAppModels(apps)
    }
    
    func clearApps() {
        storeModel.clearModels()
    }
    
    func firstFetchData() {
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=15&term=chat") else {
            return
        }
        let request = URLRequest(url: url)
        URLSession.shared.dataTask(with: request) { [weak self] data, _, error in
            if let self = self,
               let data = data,
               let result = try? JSONDecoder().decode(SearchModel.self, from: data) {
                DispatchQueue.main.async {
                    self.addApp(result.results)
                    self.loadFinished = true
                }
            }
        }.resume()
    }
    
    func loadMoreData() {
        if case .nomoredata(_) = refreshState { return }
        refreshState = .refreshing(Constants.Refresh.refreshing)
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat") else {
            return
        }
        let request = URLRequest(url: url)
        URLSession.shared.dataTask(with: request) { [weak self] data, _, error in
            if let self = self,
               let data = data,
               let result = try? JSONDecoder().decode(SearchModel.self, from: data) {
                DispatchQueue.main.async {
                    // 模拟分页
                    let start = self.storeModel.apps.count
                    let end = min(result.results.count, start + 15)
                    self.addApp(Array(result.results[start ..< end]))
                    if end == result.results.count {
                        self.refreshState = .nomoredata(Constants.Refresh.nomoredata)
                    } else {
                        self.refreshState = .normal(Constants.Refresh.normal)
                    }
                }
            }
        }.resume()
    }
    
    func refreshData() async throws {
        guard let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=15&term=chat") else {
            return
        }
        let (data, _) = try await URLSession.shared.data(from: url)
        if let result = try? JSONDecoder().decode(SearchModel.self, from: data) {
            DispatchQueue.main.async {
                self.clearApps()
                self.addApp(result.results)
            }
        }
    }
}
