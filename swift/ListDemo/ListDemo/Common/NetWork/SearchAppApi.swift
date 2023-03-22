//
//  SearchAppApi.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
import Combine
import Alamofire

protocol SearchAppAPIProtocol {
    func searchApp(quary: String, limit: Int, offset: Int) -> AnyPublisher<DataModel.AppItems, AFError>
}

//https://itunes.apple.com/search?entity=software&limit=50&offset=200&term=chat

struct SearchAppAPI: SearchAppAPIProtocol {
    func searchApp(quary: String, limit: Int, offset: Int) -> AnyPublisher<DataModel.AppItems, AFError> {
        guard let url = URL(string: ApiConstants.baseUrl + "/search" ) else {
            return Fail(error: AFError.invalidURL(url: ApiConstants.baseUrl)).eraseToAnyPublisher()
        }
        let parameters = [
            "entity": "software",
            "term": quary,
            "limit": limit,
            "offset": offset,
        ] as [String : Any]
        
        return AF.request(url,
                          method: .get,
                          parameters: parameters
        )
        .validate()
        .publishDecodable(type: DataModel.AppItems.self)
        .value()
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}
