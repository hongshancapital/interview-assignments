//
//  ImageLoadRequest.swift
//  AppList
//
//  Created by haojiajia on 2022/7/9.
//

import Foundation
import Combine

struct ImageLoadRequest {
    
    static func publisher(_ url: String) -> AnyPublisher<Data, AppError> {
        URLSession.shared
            .dataTaskPublisher(for: URL(string: url)!)
            .retry(2)
            .map{ data, _ in data }
            .mapError { AppError.networkingFailed($0) }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
    
}
