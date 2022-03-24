//
//  AppSearchListModel.swift
//  App
//
//  Created by august on 2022/3/21.
//

import Foundation
import Combine

class AppSearchListModel: ObservableObject {
    var chatAppRequest = NetworkAPI<HomeListResponse>(path: "https://itunes.apple.com/search")
    @Published @MainActor var apps = [AppInformation]()
    @Published @MainActor var hasMoreData = true
    
    func refresh(entity: String = "software", limit: Int = 50, term: String = "chat", pageSize: Int = 10) async throws {
        let response = try await chatAppRequest.fetchResponse(paramaters: ["entity":entity,"limit":limit,"term":term])
        await MainActor.run(body: {
            let count = response.results.count
            if count > 0 {
                apps.removeAll()
                if count > pageSize {
                    hasMoreData = true
                    apps.append(contentsOf: response.results.prefix(pageSize))
                } else {
                    hasMoreData = false
                    apps.append(contentsOf: response.results)
                }
            }
        })
    }
    
    func loadMore(entity: String = "software", limit: Int = 50, term: String = "chat", pageSize: Int = 10) async throws {
        let response = try await chatAppRequest.fetchResponse(paramaters: ["entity":entity,"limit":limit,"term":term])
        await MainActor.run(body: {
            let count = response.results.count
            if count > apps.count + pageSize {
                hasMoreData = true
                apps.append(contentsOf: response.results[apps.count..<apps.count.advanced(by: pageSize)])
            } else {
                hasMoreData = false
                if count > 0 {
                    apps.append(contentsOf: response.results[apps.count..<count])
                }
            }
        })
    }
}
