//
//  Network+Search.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation

extension NetworkManager {
    
    func searchChatApp(
        limit: Int
    ) async throws -> AppListResponseModel {
        let path = "/search"
        let parameters: NetworkManager.Parameters = [
            "entity": "software",
            "term": "chat",
            "limit": limit
        ]
        let value = try await self.request(path: path, parameters: parameters, decodableType: AppListResponseModel.self)
        return value
    }

}
