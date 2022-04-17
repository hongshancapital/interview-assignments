//
//  AppListViewModel.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/16/22.
//

import Foundation
import Combine

class AppListViewModel: ObservableObject {
    @Published var apps = [AppItem]()
    var noMoreData = false
    private var page = 0
    private let loadData: DataLoaderProtocol!
    
    init(onePageNum: Int = 15, loader: DataLoaderProtocol? = nil) {
        if loader == nil {
            self.loadData = DataLoader(from: "1.txt", onePageNum: onePageNum)
        }
        else {
            self.loadData = loader
        }
    }
    
    func refresh() async throws {
        page = 0
        noMoreData = false
        let newData = try await self.loadData.requestData(page: 0) ?? [AppItem]()
        await MainActor.run {
            self.apps = newData
        }
    }
    
    func loadMore() async throws {
        page += 1
        let newData = try await self.loadData.requestData(page: page) ?? [AppItem]()
        self.noMoreData = newData.isEmpty
        await MainActor.run {
            self.apps.append(contentsOf: newData)
        }
    }
}
