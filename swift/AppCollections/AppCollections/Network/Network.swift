//
//  Network.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation
import Alamofire
import os

class Network {
    
    static let shared = Network()
    
    private let host = "https://itunes.apple.com"
    
    private let decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return decoder
    }()
    
    typealias HTTPMethod = Alamofire.HTTPMethod
    typealias Parameters = Alamofire.Parameters
    
    func request<DecodableType: Decodable>(
        path: String,
        method: HTTPMethod = .get,
        parameters: Parameters? = nil,
        decodableType: DecodableType.Type = DecodableType.self
    ) async throws -> DecodableType {
        guard let url = URL(string: "\(host)\(path)") else {
            let message = "url is incorrect, host: \(host), path: \(path) "
            networkLogger.error("\(message)")
            throw NetworkError.urlIncorrect(message)
        }
        do {
            networkLogger.debug("request success, url: \(url)")
            return try await AF.request(url, method: method, parameters: parameters, requestModifier: { $0.timeoutInterval = 10 })
                .serializingDecodable(DecodableType.self).value
        } catch {
            networkLogger.error("\(error.localizedDescription)")
            throw error
        }
    }
}
