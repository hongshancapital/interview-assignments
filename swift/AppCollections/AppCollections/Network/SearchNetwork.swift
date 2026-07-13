//
//  SearchNetwork.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation

/**
 itunes search api 接口文档：https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api
 */

protocol SearchNetworkProtocol {
    func searchChatApp<DecodableType: Decodable>(
        limit: Int,
        decodableType: DecodableType.Type
    ) async throws -> DecodableType
}

class SearchNetwork: SearchNetworkProtocol {

    func searchChatApp<DecodableType: Decodable>(
        limit: Int,
        decodableType: DecodableType.Type = DecodableType.self
    ) async throws -> DecodableType {
        let path = "/search"
        let parameters: Network.Parameters = [
            "entity": "software",
            "term": "chat",
            "limit": limit
        ]
        return try await Network.shared.request(path: path, parameters: parameters, decodableType: DecodableType.self)
    }
}
