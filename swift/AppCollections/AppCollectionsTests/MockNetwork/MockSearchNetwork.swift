//
//  MockSearchNetwork.swift
//  AppCollectionsTests
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation
@testable import AppCollections

class MockSearchNetwork: SearchNetworkProtocol {
    
    var result: Result<Decodable, Error>?
    
    func searchChatApp<DecodableType: Decodable>(
        limit: Int,
        decodableType: DecodableType.Type
    ) async throws -> DecodableType {
        switch result {
        case .success(let data):
            return data as! DecodableType
        case .failure(let error):
            throw error
        case .none:
            fatalError("Not assign result before call `searchChatApp(_:)`!")
        }
    }
}
