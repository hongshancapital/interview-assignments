//
//  NetworkRequest.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import Foundation
import Combine
import OSLog

enum APIFailure: Error {
    case missingData
}

struct NetworkRequestService {
    public static let shared = NetworkRequestService()
    
    let logger = Logger(subsystem: "com.demo.application", category: "networkrequest")
    
    private init() { }
    
    func request<T: Decodable>(_ request: URLRequest) -> AnyPublisher<[T], Never> {
        let publisher = URLSession.shared.dataTaskPublisher(for: request)
#if DEBUG
            .handleEvents(receiveSubscription: {
                self.logger.debug("receiveSubscription event called with \(String(describing: $0))")
            }, receiveOutput: {
                self.logger.debug("receiveOutput was invoked with \(String(describing: $0))")
            }, receiveCompletion: {
                self.logger.debug("receiveCompletion event called with \(String(describing: $0))")
            }, receiveCancel: {
                self.logger.debug("receiveCancel event invoked")
            }, receiveRequest: {
                self.logger.debug("receiveRequest event called with \(String(describing: $0))")
            })
#endif
            .tryMap { data, response -> Data in
                guard let httpResponse = response as? HTTPURLResponse,
                      httpResponse.statusCode == 200 else {
                    throw APIFailure.missingData
                }
                return data
            }
            .retry(2)
            .decode(type: Response<T>.self, decoder: JSONDecoder())
            .map {
                return $0.items
            }
            .replaceError(with: [])
            .eraseToAnyPublisher()
        
        return publisher
    }
}
