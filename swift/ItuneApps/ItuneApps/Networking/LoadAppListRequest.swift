//
//  LoadAppListRequest.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import Combine
import Foundation
import SwiftUI

let appDecoder: JSONDecoder = {
    let decoder = JSONDecoder()
    decoder.keyDecodingStrategy = .convertFromSnakeCase
    return decoder
}()

struct LoadAppListRequest {
    var queryLimit: Int = 10
    var baseUrl: String = "https://itunes.apple.com/search?entity=software&term=chat&limit="
    var queryUrlStr: String {
        return baseUrl + "\(queryLimit)"
    }

    var publisher: AnyPublisher<AppModel, Error> {
        let url = URL(string: queryUrlStr)!
        return URLSession.shared
            .dataTaskPublisher(for: url)
            .map { $0.data }
            .decode(type: AppModel.self, decoder: appDecoder)
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}
