//
//  Requester.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/5.
//

import Foundation
import Combine

enum RequestError: Error {
    
    
    case badUrlError
    case decodeError(_ error: Error)
    case networkError(_ error: Error)
    case unknown
}

struct Requester<T: Decodable> {
    
    static func fetchRemoteDatas(_ urlString: String) async throws -> [T] {
        guard let url = URL(string: urlString) else {
            throw RequestError.badUrlError
        }
        
        let data: Data
        do {
            (data, _) = try await URLSession.shared.data(from: url)
        } catch let error {
            throw RequestError.networkError(error)
        }
        
        let rsp: Response
        do {
            rsp = try JSONDecoder().decode(Response.self, from: data)
        } catch let error {
            throw RequestError.decodeError(error)
        }
        return rsp.results
    }
    
    struct Response: Decodable {
        var resultCount: Int
        var results: [T]
    }
}
