//
//  LoadAppListRequest.swift
//  Demo
//
//  Created by Kai on 2022/2/22.
//

import Foundation
import Combine


struct LoadAllListRequest {
    
    func publisher() -> AnyPublisher<KKData, AppError> {
        
        URLSession.shared
            .dataTaskPublisher(for: URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!)
            .map ( \.data )
            .decode(type: KKData.self, decoder: JSONDecoder())
            .mapError {
                AppError.loadListFailed($0)
            }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
    
}


