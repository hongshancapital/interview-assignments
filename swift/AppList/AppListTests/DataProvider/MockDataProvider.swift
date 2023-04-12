import Foundation
@testable import AppList

class MockDataProvider: DataProvider {
    
    var shouldLoadSuccess = true
    var shouldFetchSuccess = true
    var isNoMoreData = false
    
    func loadCardList() async throws {
        try await Task.sleep(nanoseconds: NSEC_PER_SEC)
        if !shouldLoadSuccess {
            throw GeneralError(code: 999, message: "Load error")
        }
    }
    
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel] {
        try await Task.sleep(nanoseconds: NSEC_PER_SEC)
        if shouldFetchSuccess {
            if isNoMoreData { return [] }
            return Array(repeating: CardViewModel.mockViewModel, count: 15)
        } else {
            throw GeneralError(code: 1000, message: "fetch error")
        }
    }
    
}
