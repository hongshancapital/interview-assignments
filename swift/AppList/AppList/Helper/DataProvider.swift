import Foundation
protocol DataProvider {
    func loadCardList() async throws
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel]
}

class CardListDataProvider: DataProvider {

    private var allData: CardListModel?
    private let decoder = JSONDecoder()
    
    func loadCardList() async throws {
        allData = try await loadData(from: CardPageLoaderConfig.cardList)
    }
    
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel] {
        try await Task.sleep(nanoseconds: 2 * NSEC_PER_SEC)
        
        guard from >= 0, stride > 0 else {
            throw GeneralError(code: 400, message: "Illegal request parameter, from: \(from), stride: \(from)")
        }
        
        guard let allData, from < allData.resultCount else {
            return []
        }
        
        let slice = allData
            .results[from..<min(from + stride, allData.resultCount)]
            .map { CardViewModel(model: $0) }
        return Array(slice)
    }
    
    private func loadData<T: Decodable>(from config: LoaderConfig) async throws -> T {
        let model = try await withUnsafeThrowingContinuation { continuation in
            do {
                let data = try Data(contentsOf: config.url)
                let jsonModel = try decoder.decode(T.self, from: data)
                continuation.resume(returning: jsonModel)
            } catch {
                continuation.resume(throwing: error)
            }
        }
        return model
    }
}

struct GeneralError: Error {
    let code: Int
    let message: String
}

protocol LoaderConfig {
    var url: URL { get }
}

enum CardPageLoaderConfig: LoaderConfig {
    case cardList

    var url: URL {
        Bundle.main.url(forResource: "mock.json", withExtension: nil)!
    }
}

