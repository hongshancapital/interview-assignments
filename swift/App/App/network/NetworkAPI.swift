//
//  NetworkService.swift
//  App
//
//  Created by august on 2022/3/21.
//

import Combine
import Foundation

enum NetworkError: Error {
    case invalidURL(String)
    case requestError(String)
    case unSupportedMethod(String)
}

class NetworkAPI<T: Decodable> {
    
    var path: String
    
    private lazy var session: URLSession = {
        let queue = OperationQueue()
        queue.name = "com.august.app.networkservice"
        return URLSession(configuration: .default, delegate: nil, delegateQueue: queue)
    }()
        
    init(path: String) {
        self.path = path
    }
    
    func fetchResponse(with method: String = "GET", paramaters: [String:Any]? = nil) async throws -> T {
        guard let url = URL(string: path) else {
            throw NetworkError.invalidURL(path)
        }
        var request = URLRequest(url: url)
        request.httpMethod = method
        if method.lowercased() == "get" {
            if let paramaters = paramaters {
                var components = URLComponents(string: path)
                components?.queryItems = paramaters.map { URLQueryItem(name: $0.key, value: "\($0.value)") }
                if let finalUrl = components?.url {
                    request.url = finalUrl
                }
            }
        } else if method.lowercased() == "post" {
            if let paramaters = paramaters {
                request.httpBody = try JSONSerialization.data(withJSONObject: paramaters)
            }
        } else {
            throw NetworkError.unSupportedMethod(method)
        }
       
        let (data, response) = try await session.data(for: request)
        if let httpResponse = response as? HTTPURLResponse {
            if httpResponse.statusCode != 200 {
                throw NetworkError.requestError(httpResponse.debugDescription)
            }
        }
        let result = try JSONDecoder().decode(T.self, from: data)
        return result
    }
}
