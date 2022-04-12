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
    case dataDecodeFailed
    case unSupportMethod
}

class Network {
    
    static let shared = Network()
    private let decoder = JSONDecoder()
    private let timeoutInterval: TimeInterval = 10
    
    func requestData<T: Decodable> (method: String = "GET", urlPath: String, params: [String: Any]? = nil) async throws -> T {
        let url = URL(string: urlPath)
        guard let url = url else {
            throw NetworkError.urlError
        }
        
        var request = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval: timeoutInterval)
        let upperMethod = method.uppercased()
        request.httpMethod = upperMethod
        if upperMethod == "GET" {
            request.url = createURL(urlPath: urlPath, params: params)
        } else if upperMethod == "POST" {
            if let params = params {
                request.httpBody = try JSONSerialization.data(withJSONObject: params)
            }
        } else {
            throw NetworkError.unSupportMethod
        }
        
        let result: NetworkResult
        do {
            result = try await URLSession.shared.data(for: request)
        } catch {
            throw NetworkError.requestFailed
        }
        
        do {
            return try decoder.decode(T.self, from: result.data)
        } catch {
            throw NetworkError.dataDecodeFailed
        }
    }
    
    private func createURL(urlPath: String, params: [String: Any]? = nil) -> URL? {
        guard var components = URLComponents(string: urlPath) else {
            return nil
        }
        
        guard let params = params else {
            return components.url
        }
        
        components.queryItems = params.map {
            URLQueryItem(name: $0.key, value: "\($0.value)")
        }
        return components.url
    }
}
