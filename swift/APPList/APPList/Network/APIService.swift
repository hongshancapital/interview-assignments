//
//  APIService.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import Foundation
import Combine

enum ContentType: String {
    case JSON = "application/json"
}

enum HttpHeaders: String {
    case contentType = "Content-Type"
}

enum HttpError: Error {
    case badURL, badResponse, errorDecodingData, invalidURL
}

class APIService {
    static let shared = APIService()
    
    func fetch<T: Codable>(url: URL) async throws -> T {
        let (data, response) = try await URLSession.shared.data(from: url)
        
        guard (response as? HTTPURLResponse)?.statusCode == 200 else {
            throw HttpError.badResponse
        }
        
        guard let object = try? JSONDecoder().decode(T.self, from: data) else {
            throw HttpError.errorDecodingData
        }
        return object
    }
    
    func sendData<T: Codable>(to url: URL, params: [String: Any], httpMethod: String) async throws -> T {
        var request = URLRequest(url: url)
        
        request.httpMethod = httpMethod
        request.addValue(ContentType.JSON.rawValue,
                         forHTTPHeaderField: HttpHeaders.contentType.rawValue)
        
        request.httpBody = try? JSONSerialization.data(withJSONObject: params)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard (response as? HTTPURLResponse)?.statusCode == 200 else {
            throw HttpError.badResponse
        }
        
        guard let object = try? JSONDecoder().decode(T.self, from: data) else {
            throw HttpError.errorDecodingData
        }
        return object
    }
    
}
