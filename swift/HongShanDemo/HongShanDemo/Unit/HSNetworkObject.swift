//
//  HSNetworkObject.swift
//  HongShanDemo
//
//  Created by 木木 on 2022/5/15.
//

import Foundation
import Combine

class HSNetworkObject{
    
    static let shared = HSNetworkObject()
    
    private init() {
        
    }
    
    func getData() -> AnyPublisher<[HSModel], Never> {
        let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")
        guard let url = url else {
            return Just([]).eraseToAnyPublisher()
        }
        
        return URLSession.shared.dataTaskPublisher(for: url)
            .filter { ($0.response as! HTTPURLResponse).statusCode == 200 }
            .map { $0.data }
            .decode(type: [HSModel].self, decoder: JSONDecoder())
            .catch { _ in
                Just([])
            }
            .eraseToAnyPublisher()
    }
}
