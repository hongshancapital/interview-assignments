//
//  Network.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation
import Alamofire

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
            let message = "[Error] Network url is not correct, host: \(host), path: \(path) "
            log(.error, message: message, component: .network)
            fatalError(message)
        }
        do {
            return try await AF.request(url, method: method, parameters: parameters).serializingDecodable(DecodableType.self).value
        } catch {
            log(.error, message: error.localizedDescription, component: .network)
            throw error
        }
        
    }
}
