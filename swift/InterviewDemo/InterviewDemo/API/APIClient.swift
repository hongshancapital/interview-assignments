//

//
//  APIClient.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/3.
//  
//
    

import Foundation
import Combine

struct APIClient {
    // 网络请求
    func request<T: Decodable>(_ request: URLRequest) -> AnyPublisher<T, Error> {
        return URLSession.shared.dataTaskPublisher(for: request)
            .tryMap { data, _ in
                let value = try JSONDecoder().decode(T.self, from: data)
                return value
            }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}
