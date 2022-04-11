//
//  Network.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import Foundation
import Combine

typealias NetworkResult = (data: Data, response: URLResponse)

enum NetworkError: Error {
    case urlError
    case requestFailed
    case decodeFailed
}

class Network {
    static let shared = Network()
    private let decoder = JSONDecoder()
    
    func requestData<T: Decodable> (host: String, urlPath: String, params: [String: Any]) async throws -> T {
        let url = createURL(host: host, urlPath: urlPath, params: params)
        guard let url = url else {
            throw NetworkError.urlError
        }
        
        var request = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval: 10)
        request.httpMethod = "GET"
        
        let result: NetworkResult
        do {
            result = try await URLSession.shared.data(for: request)
        } catch {
            throw NetworkError.requestFailed
        }
        
        do {
            return try decoder.decode(T.self, from: result.data)
        } catch {
            throw NetworkError.decodeFailed
        }
    }
    
    private func createURL(host: String, urlPath: String, params: [String: Any]) -> URL? {
        guard var components = URLComponents(string: host + urlPath) else {
            return nil
        }
        
        var queryItems = [URLQueryItem]()
        for param in params {
            let queryItem = URLQueryItem(name: param.key, value: "\(param.value)")
            queryItems.append(queryItem)
        }
        components.queryItems = queryItems
        return components.url
    }
}
