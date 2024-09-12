//
// Homework
// AppListViewModel.swift
//
// Created by wuyikai on 2022/4/27.
// 
// 

import Foundation

class AppStore: ObservableObject {
    @MainActor @Published var apps: [AppInfo] = []
    @MainActor @Published var nomoreData: Bool = false
    @MainActor private var curragePage = 0
    
    init() {
        Task {
            try? await reload()
        }
    }
    
    func reload() async throws {
        await update(page: 0)
        await update(nomoreData: false)
        try await loadNextPage()
    }
    
    func loadNextPage() async throws {
        if await nomoreData {
            return
        }
        let page = await curragePage
        let request = AppListRequest(page: page)
        let response = await APIManager().send(request)
        switch response {
        case .failure(APIError.ResponseError.nomoreData(_)):
            await update(nomoreData: true)
        case .success(let data):
            let appListModel = try JSONDecoder().decode(AppListMode.self, from: data)
            let apps = appListModel.results.map({ model in
                AppInfo(title: model.trackName,
                        description: model.description,
                        icon: model.artworkUrl100)
            })
            var originalApps = await self.apps
            if page == 0 {
                await update(apps: apps)
            } else {
                originalApps.append(contentsOf: apps)
                await update(apps: originalApps)
            }
            await update(page: page + 1)
        default: break
        }
    }
    
    @MainActor private func update(apps: [AppInfo]) {
        self.apps = apps
    }
    
    @MainActor private func update(nomoreData: Bool) {
        self.nomoreData = nomoreData
    }
    
    @MainActor private func update(page: Int) {
        self.curragePage = page
    }
}

private struct AppInfoModel: Decodable {
    let trackName: String
    let description: String
    let artworkUrl100: String
}

private struct AppListMode: Decodable {
    let results: [AppInfoModel]
}
