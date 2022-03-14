//
//  ApiService.swift
//  AppStore
//
//  Created by Ma on 2022/3/13.
//

import Foundation
import Combine

enum ApiServiceError: Error {
    case networkError
    case responseError(Error)
    case parseError(Error)
}

class ApiService {
    func request<Res>(req:URLRequest,res:Res.Type) -> AnyPublisher<Res,ApiServiceError> where Res : Decodable  {
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        return URLSession.shared.dataTaskPublisher(for: req)
            .mapError({ error -> ApiServiceError in
                return ApiServiceError.networkError
            })
            .map { data,response in
                return data
            }
            .flatMap({ data in
                Just(data).decode(type: res.self, decoder: jsonDecoder)
                    .mapError(ApiServiceError.parseError)
            })
            .receive(on: RunLoop.main)
            .eraseToAnyPublisher()
    }
}
