import Foundation
import Combine

protocol DataProvider {
    func loadCardList() async
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel]
    var loadDataSubject: PassthroughSubject<Bool, Error> { get }
}

class CardListDataProvider: DataProvider {
    var loadDataSubject = PassthroughSubject<Bool, Error>()
    
    private let decoder = JSONDecoder()
    private var cardListModel: CardListModel?
    
    func loadCardList() async {
        do {
            cardListModel = try await loadData(from: CardPageLoaderConfig.cardList)
            loadDataSubject.send(completion: .finished)
        } catch {
            loadDataSubject.send(completion: .failure(GeneralError.loadData))
        }
    }
    
    func fetchData(from: Int, stride: Int) async throws -> [CardViewModel] {
        try await Task.sleep(nanoseconds: 2 * NSEC_PER_SEC)
        
        guard from >= 0,
              stride > 0 else {
            throw GeneralError(code: 400, message: "Illegal request parameter, from: \(from), stride: \(from)")
        }
        
        guard let cardListModel,
              from < cardListModel.resultCount else {
            return []
        }
        
        let slice = cardListModel
            .results[from..<min(from + stride, cardListModel.resultCount)]
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

