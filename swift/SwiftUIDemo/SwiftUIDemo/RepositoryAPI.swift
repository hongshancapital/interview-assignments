//
//  RepositoryAPI.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation
import Combine

/// 网络请求api
enum RepositoryAPI {
    typealias SearchResponse = AnyPublisher<Result<[SearchResultModel], ErrorResponse>, Never>
    typealias SendRequest = (URLRequest) -> AnyPublisher<Data, URLSessionError>
 
    static func search(query: String) -> SearchResponse {
        search(query: query, sendRequest: URLSession.shared.combine.send)
    }
    
    static func search(query: String, sendRequest: SendRequest) -> SearchResponse {
           
        guard var components = URLComponents(string: "http://fun.haylou.com/sport/day/page") else {
            return .empty()
        }
        components.queryItems = [URLQueryItem(name: "q", value: query)]

        guard let url = components.url else {
            return .empty()
        }

        var request = URLRequest(url: url)
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        
        
        return sendRequest(request)
            .decode(type: ItemResponse<SearchResultModel>.self, decoder: decoder).map({ res in
                return Result<[SearchResultModel], ErrorResponse>.success(res.items)
            })
            .catch { error -> SearchResponse in
                guard case let .serverErrorMessage(_, data)? = error as? URLSessionError else {
                    return .just(.success([]))
                }
                do {
                    let response = try JSONDecoder().decode(ErrorResponse.self, from: data)
                    return .just(.failure(response))
                } catch _ {
                    return .just(.success([]))
                }
            }
            .eraseToAnyPublisher()
    }
    
}
