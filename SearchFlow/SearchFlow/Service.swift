//
//  Service.swift
//  SearchFlow
//
//  Created by evan on 2021/1/7.
//

import Foundation
import Combine

struct SearchSever {
    let service = Service()
}

class Service {
    func searchPublisher(matching text: String) -> AnyPublisher<[Brands.Series], Error> {
        guard let url = URL(string: "https://evan-0723.github.io/evan.github.io/files/Brands.json") else { preconditionFailure() }
        return URLSession.shared
            .dataTaskPublisher(for: url)
            .handleEvents(receiveOutput: { print(NSString(data: $0.data, encoding: String.Encoding.utf8.rawValue)!) })
            .map { $0.data }
            .decode(type: Brands.self, decoder: JSONDecoder())
            .map { result in result.brands.first { $0.name.lowercased() == text.lowercased() }?.series ?? [] }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}
