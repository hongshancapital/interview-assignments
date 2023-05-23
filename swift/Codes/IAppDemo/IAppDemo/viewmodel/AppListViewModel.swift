//
//  AppListViewModel.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation
import SwiftUI
@MainActor class AppListViewModel: ObservableObject {
    
    lazy var searchNetwork: NetworkManager = NetworkManager.shared
    private let pageSize = 20
    
    @Published var list: [ListItemModel] = []
    @Published var isFirstLoading = true
    @Published var noMoreData = false
    @Published var showError = false
    
    private(set) var alertMessage: String = "" {
        didSet { showError = true }
    }
    
    func firstLoad() async {
        await refresh()
        isFirstLoading = false
    }
    
    func refresh() async {
        do {
            let results = try await searchNetwork.searchChatApp(limit: pageSize).results
            list = results
            if list.isEmpty {
                alertMessage = "No available data."
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
    
    func loadMore() async {
        do {
            let currentCount = list.count
            // just mock more data
            let results = try await searchNetwork.searchChatApp(
                limit: currentCount + pageSize).results
            
            let filteredResults = results.filter { result in
                !list.contains(where: { $0.trackId == result.trackId })
            }
            
            if !filteredResults.isEmpty {
                list.append(contentsOf: filteredResults)
            } else {
                noMoreData = true
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
}
