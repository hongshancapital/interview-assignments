//
//  VMData.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import Combine
import SwiftUI

final class ApplicationViewModel: ObservableObject {
    @Published var applications: [Application] = []
    @Published var isLoading: Bool = false
    @Published var pagination: Pagination = Pagination(size: 10)
    
    init() {
        Task {
            await self.fetchApplications()
        }
    }
    
    @MainActor
    private func populate(apps: [Application]) async {
        self.applications += apps
        self.isLoading = false
        self.syncUserDefault()
    }
    
    func fetchApplications() async {
        if !hasMore {
            return
        }
        self.isLoading = true
        if applications.count != 0 {
            pagination.nextPage()
        }
        Task {
            let apps = await APIManager.fetchApplicationData(pagination: self.pagination) ?? [];
            await populate(apps: apps)
        }
    }
    
    func syncUserDefault() {
        var dict = UserDefaults.standard.dictionary(forKey: APPLICATIONKEY) ?? [:] as [String:Bool]
        applications.forEach { application in
            let formatKey = String(format: APPLICATIONKEYIDF, application.id)
            dict[formatKey] = application.isFavorite
        }
        UserDefaults.standard.set(dict, forKey: APPLICATIONKEY)
        UserDefaults.standard.synchronize()
    }
    
    var hasMore: Bool {
        return pagination.hasMore
    }
    
    func refresh() async {
        self.pagination.reset()
        self.applications.removeAll()
        await fetchApplications()
    }
}

