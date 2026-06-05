//
//  Network.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import Foundation

class Network {
    
    enum Method {
        case get
        case post
    }
    
    enum NetworkError: Error {
    case invalidUrl(String)
    case requestFaild(String)
    }
    
    static let shared = Network()
    
    private lazy var urlSession: URLSession = {
        let queue = OperationQueue()
        queue.name = "com.demo.app.network"
        return URLSession(configuration: .default, delegate: nil, delegateQueue: queue)
    }()
    
    func fetch<T: Decodable>(_ path: String, method: Method = .get, paramas: [String: AnyHashable] = [:]) async throws -> T {
        guard let url = URL(string: path) else {
            throw NetworkError.invalidUrl(path)
        }
        var request = URLRequest(url: url)
        switch method {
        case .get:
            request.httpMethod = "get"
            var components = URLComponents(url: url, resolvingAgainstBaseURL: false)
            components?.queryItems = paramas.map({ (key, value) in
                return URLQueryItem(name: key, value: "\(value)")
            })
            request.url = components?.url
        case .post:
            request.httpMethod = "post"
            request.httpBody = try JSONSerialization.data(withJSONObject: paramas, options: .fragmentsAllowed)
        }
        let (data, response) = try await urlSession.data(for: request)
        if let response = response as? HTTPURLResponse {
            if response.statusCode != 200 {
                // http code 200 success
                throw NetworkError.requestFaild(response.debugDescription)
            }
        }
        let model = try JSONDecoder().decode(T.self, from: data)
        return model
    }
}
