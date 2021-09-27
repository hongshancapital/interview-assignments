//
//  ViewModel.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/26.
//

import Foundation
import Combine

class ViewModel: ObservableObject {

    /// 搜索结果列表数据
    @Published var listData: StockInfo?
    
    /// 是否进行搜索标志
    /// - true : 搜索
    /// - false : 未搜索
    @Published var isSearch: Bool = false
    
    /// 搜索关键字Publisher，用于启动Combine链
    var searchPublisher = PassthroughSubject<String, Never>()
    private var cancellable = Set<AnyCancellable>()

    init() {
        
        // MARK: - 启动Server监听
        DispatchQueue.global().async {
            IOSHttpServer.shared.beginListen()
        }
        
        // MARK: - 通过PassthroughSubject订阅关键字变化
        //         通过flatMap转换Publisher为Publisher<StockInfo, Error>
        //         通过search查询关键字
        searchPublisher
            .flatMap { searchContent in
                return self.search(searchContent)
            }
            .receive(on: RunLoop.main)
            .sink { completion in
                switch completion {
                case .finished:
                    print("finished")
                case .failure(let error):
                    print(error.localizedDescription)
                }
            } receiveValue: { [weak self] searchResult in
                self?.listData = searchResult
            }
            .store(in: &cancellable)
    }
    
    /// 按照关键字搜索
    ///
    /// - Parameters:
    ///   - text: 关键字
    /// - Returns: AnyPublisher<StockInfo, Error>
    public func search(_ text: String) -> AnyPublisher<StockInfo, Error> {
        
        let url = URL(string: "http://[::1]:8080/api/v2/users?search=" + text)
        guard let url = url else {
            return Fail(error: NetworkError.badURL).eraseToAnyPublisher()
        }
        
        return URLSession.shared.dataTaskPublisher(for: url)
            .filter { ($0.response as! HTTPURLResponse).statusCode == 200 }
            .map { $0.data }
            .decode(type: StockInfo.self, decoder: JSONDecoder())
            .catch { _ in
                Fail(error: NetworkError.decodeError)
            }
            .eraseToAnyPublisher()
    }
}
