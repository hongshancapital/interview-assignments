//
//  Network.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation
import Alamofire
import Kingfisher
import os

let logger = Logger(subsystem: Bundle.main.bundleIdentifier ?? "", category: "main")

let networkLogger = Logger(subsystem: Bundle.main.bundleIdentifier ?? "", category: "network")

class NetworkConfig {
    static let config: NetworkConfig = .init()
    private init(){}
    var timeoutInterval: TimeInterval = 30
}

class NetworkManager {
    static let shared = NetworkManager()
    private let host = "https://itunes.apple.com"
    
    fileprivate lazy var decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return decoder
    }()
    
    typealias HTTPMethod = Alamofire.HTTPMethod
    typealias Parameters = Alamofire.Parameters
    
    func request<T>(
        path: String,
        method: HTTPMethod = .get,
        parameters: Parameters? = nil,
        decodableType: T.Type = T.self
    ) async throws -> T  where T: Decodable {
        
        guard let url = URL(string: "\(host)\(path)") else {
            let message = "url is error:\(host)===>\(path)"
            networkLogger.error("\(message)")
            throw NetworkError.urlIncorrect(message)
        }
        do {
            networkLogger.debug("request success url ===> \(url)")
            return try await AF.request(url,
                                        method: method,
                                        parameters: parameters,
                                        requestModifier: {
                $0.timeoutInterval = NetworkConfig.config.timeoutInterval
            }).serializingDecodable(T.self).value
        } catch {
            networkLogger.error("\(error.localizedDescription)")
            throw error
        }
    }
}
