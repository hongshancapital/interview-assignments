import Foundation
import Combine
@testable import AppList

class MockDataProvider: DataProvider {
    
    var loadDataSubject = PassthroughSubject<Bool, Error>()
    
    var shouldLoadSuccess = true
    var shouldFetchSuccess = true
    var isNoMoreData = false
    
    func loadCardList() async {
        do {
            try await Task.sleep(nanoseconds: NSEC_PER_SEC)
            if shouldLoadSuccess {
                loadDataSubject.send(completion: .finished)
            } else {
                loadDataSubject.send(completion: .failure(GeneralError.loadData))
            }
        } catch {
            loadDataSubject.send(completion: .failure(GeneralError.loadData))
        }
    }
    
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel] {
        try await Task.sleep(nanoseconds: 2 * NSEC_PER_SEC)
        if shouldFetchSuccess {
            if isNoMoreData { return [] }
            return Array(repeating: CardViewModel.mockViewModel, count: 15)
        } else {
            throw GeneralError.fetchData
        }
    }
    
}
