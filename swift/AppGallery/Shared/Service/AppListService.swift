//
//  AppListService.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

protocol AppListService {
    
    func fetchApps(limit: Int) async throws -> [ChatApp]
    
}

class RemoteAppListService: AppListService {
    
    private let maxLimitValue = 50
    
    func fetchApps(limit: Int) async throws -> [ChatApp] {
        let finalLimit = processLimit(limit)
        let theURL = getRequestUrl(with: finalLimit)
        let (data, _) = try await URLSession.shared.data(from: theURL)
        let response = try JSONDecoder().decode(QueryResponse<ChatApp>.self, from: data)
        return response.results
    }
    
    private func processLimit(_ limit: Int) -> Int {
        return min(max(limit, 0), maxLimitValue)
    }
    
    private func getRequestUrl(with limit: Int) -> URL {
        let urlString = "https://itunes.apple.com/search?entity=software&limit=\(limit)&term=chat"
        return URL(string: urlString)!
    }
    
}
