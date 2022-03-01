//
//  MockWithErrorAPIService.swift
//  ListDemoTests
//
//  Created by Chr1s on 2022/3/1.
//

import Foundation
import Combine
@testable import ListDemo

class MockWithErrorAPIService: ApiProtocol {
    
    var errorType: Int = 0
    
    init(errorType: Int) {
        self.errorType = errorType
    }
    
    func fetchListData() -> AnyPublisher<[ListCell], FetchListError> {
        switch errorType {
        case 1:
            return Fail(error: FetchListError.fileNotExist).eraseToAnyPublisher()
        case 2:
            let error = NSError(domain: "2", code: 2)
            return Fail(error: FetchListError.fileReadError(error: error)).eraseToAnyPublisher()
        case 3:
            let error = NSError(domain: "3", code: 3)
            return Fail(error: FetchListError.decodeError(error: error)).eraseToAnyPublisher()
        case 4:
            return Fail(error: FetchListError.unknown).eraseToAnyPublisher()
        default:
            return Fail(error: FetchListError.unknown).eraseToAnyPublisher()
        }

    }
    
    func fetchFavoriteData() -> [Bool] {
        return []
    }
    
    func updateListData(id: Int, isFavorite: Bool) -> AnyPublisher<[Bool], Never> {
        return Just([]).eraseToAnyPublisher()
    }
}
