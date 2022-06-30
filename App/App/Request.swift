//
//  Request.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import Foundation
import Combine

struct Request {

    struct myResponse<T> {
        let value: T
        let response: URLResponse
    }

    func run<T: Decodable>(_ request: URLRequest) -> AnyPublisher<myResponse<T>, Error> {
        return URLSession.shared
            .dataTaskPublisher(for: request)
            .tryMap { result -> myResponse<T> in
                let value = try JSONDecoder().decode(T.self, from: result.data)
                return myResponse(value: value, response: result.response)
            }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}
