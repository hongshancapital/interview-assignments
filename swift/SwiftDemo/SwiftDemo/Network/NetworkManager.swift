//
//  NetworkManager.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/9.
//

import Foundation

enum HTTPMethod: String {
    case get = "GET"
    case post = "POST"
}

enum NetworkError: Error {
    case invalidURL
    case invalidResponse
    case requestFailed(message: String)
}

struct Resource<T> {
    let url: URL
    let method: HTTPMethod
    let parse: (Data) -> T?
}

typealias ResponseAction<T> = (_ t: T?)->Void

class NetworkManager {
    static let shared = NetworkManager()
    static let API_HOST = "swift.demo"
    
    private let session: URLSession
    
    private init() {
        
        if CommandLine.arguments.contains(MockURLProtocol.MockArguments) {
            URLProtocol.registerClass(MockURLProtocol.self)
        }
        
        let configurationWithMock = URLSessionConfiguration.default
        configurationWithMock.protocolClasses?.insert(MockURLProtocol.self, at: 0)

        self.session = URLSession(configuration: configurationWithMock)
    }
    
    func load<T>(resource: Resource<T>, completion: @escaping (Result<T, NetworkError>) -> Void) {
        
        var request = URLRequest(url: resource.url)
        request.httpMethod = resource.method.rawValue
        let task = session.dataTask(with: request) { data, response, error in
            guard let data = data, let response = response as? HTTPURLResponse else {
                completion(.failure(.invalidResponse))
                return
            }
            
            if 200...299 ~= response.statusCode {
                if let result = resource.parse(data) {
                    completion(.success(result))
                } else {
                    completion(.failure(.requestFailed(message: "Unable to parse response data")))
                }
            } else {
                completion(.failure(.requestFailed(message: "Request failed with status code \(response.statusCode)")))
            }
        }
        task.resume()
    }
    
    static func loadFeeds(block: @escaping ResponseAction<ModelFeeds>){
        let postsURL = URL(string: "https://\(Self.API_HOST)/feeds")!
        let resource = Resource<ModelFeeds>(url: postsURL, method: .get, parse: { data in
            let decoder = JSONDecoder()
            return try? decoder.decode(ModelFeeds.self, from: data)
            
        })

        NetworkManager.shared.load(resource: resource) { result in
            switch result {
            case .success(let posts):
                DispatchQueue.main.async {
                    block(posts)
                }
            case .failure(let error):
                print("load feeds error: \(error)")
                DispatchQueue.main.async {
                    block(nil)
                }
            }
        }

    }
    
    
    static func testExample() {
        let path = "/some/path"
        let fileName = "feeds.txt"
        URLProtocol.registerClass(MockURLProtocol.self)
        MockURLProtocol.mockResponses = [path: fileName]
        
        let url = URL(string: "https://example.com" + path)!
        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: url) { data, response, error in
            if let data = data {
                print("Data: \(String(data: data, encoding: .utf8)!)")
            }
            
        }
        task.resume()
        
    }

}
