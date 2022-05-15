//
//  APIService.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Foundation

public struct APIService {
    let baseURL = URL(string: "https://itunes.apple.com/")!
    public static let shared = APIService()
    var timeoutInteval: TimeInterval = 30

    public enum APIError: Error {
        case noResponse
        case jsonDecodingError(error: Error)
        case networkError(error: Error)
    }

    public enum Endpoint {
        case search
        func path() -> String {
            switch self {
            case .search:
                return "search"
            }
        }
    }

    public func GET(endpoint: Endpoint,
                    params: [String: String]?,
                    completionHandler: @escaping (Result<Dictionary<String, Any>, APIError>) -> Void) {
        let queryURL = baseURL.appendingPathComponent(endpoint.path())
        var components = URLComponents(url: queryURL, resolvingAgainstBaseURL: true)!
        addCommonParams(components: &components)
        if let params = params {
            for (_, value) in params.enumerated() {
                components.queryItems?.append(URLQueryItem(name: value.key, value: value.value))
            }
        }
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        request.timeoutInterval = timeoutInteval
        let task = URLSession.shared.dataTask(with: request) { data, _, error in
            guard let data = data else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.noResponse))
                }
                return
            }
            guard error == nil else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.networkError(error: error!)))
                }
                return
            }
            do {
                let object = try JSONSerialization.jsonObject(with: data) as? [String: Any]
                DispatchQueue.main.async {
                    completionHandler(.success(object ?? Dictionary<String, Any>()))
                }
            } catch let error {
                DispatchQueue.main.async {
                    #if DEBUG
                        print("JSON Decoding Error: \(error)")
                    #endif
                    completionHandler(.failure(.jsonDecodingError(error: error)))
                }
            }
        }
        task.resume()
    }

    func addCommonParams(components: inout URLComponents) {
        components.queryItems = [
            URLQueryItem(name: "language", value: Locale.preferredLanguages[0]),
        ]
    }
}
