//
//  AppSearchListModel.swift
//  App
//
//  Created by august on 2022/3/21.
//

import Foundation
import Combine

class AppSearchListModel: ObservableObject {
    var chatAppRequest = NetworkAPI<HomeListResponse>(path: "https://itunes.apple.com/search?entity=software&limit=50&term=game")
    @Published var apps = [AppInformation]()
    @Published @MainActor var hasMoreData = true
    
    func refresh(limit: Int = 10) async throws {
        let response = try await chatAppRequest.fetchResponse()
        await MainActor.run(body: {
            let count = response.results.count
            if count > 0 {
                apps.removeAll()
                if count > limit {
                    hasMoreData = true
                    apps.append(contentsOf: response.results.prefix(limit))
                } else {
                    hasMoreData = false
                    apps.append(contentsOf: response.results)
                }
            }
        })
    }
    
    func loadMore(limit: Int = 10) async throws {
        let response = try await chatAppRequest.fetchResponse()
        await MainActor.run(body: {
            let count = response.results.count
            if count > apps.count + limit {
                hasMoreData = true
                apps.append(contentsOf: response.results[apps.count..<apps.count.advanced(by: limit)])
            } else {
                hasMoreData = false
                apps.append(contentsOf: response.results[apps.count..<count])
            }
        })
    }
}
