//
//  MockService.swift
//  ListDemoTests
//
//  Created by Chr1s on 2022/2/25.
//

import Foundation
import Combine
@testable import ListDemo

class MockAPIService: ApiProtocol {
    
    func fetchListData() -> AnyPublisher<[ListCell], FetchListError> {
        
        var data: [ListCell] = []
        
        for i in 0..<30 {
            data.append(ListCell(trackId: i, artworkUrl60: "", trackName: "name\(i)", description: "Description\(i)"))
        }
        return Just(data).setFailureType(to: FetchListError.self).eraseToAnyPublisher()
    }
    
    func fetchFavoriteData() -> [Bool] {
        let data: [Bool] = [Bool](repeating: false, count: 30)
        return data
    }
    
    func updateListData(id: Int, isFavorite: Bool) -> AnyPublisher<[Bool], Never> {
        var data: [Bool] = [Bool](repeating: false, count: 30)
        data[id] = isFavorite
        return Just(data).eraseToAnyPublisher()
    }
}
