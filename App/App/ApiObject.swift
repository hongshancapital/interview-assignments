//
//  ApiObject.swift
//  App
//
//  Created by xiongjin on 2022/6/29.
//

import Foundation
import Combine

enum ApiObject {
    static let apiRequest = Request()
    static let baseUrl = URL(string: "https://itunes.apple.com/search?entity=software&limit=30&term=chat")!
}

enum APIPath: String {
    case resourceList = "resource/list"
}

extension ApiObject {

    static func request(_ path: APIPath) -> AnyPublisher<Response, Error> {
        guard let components = URLComponents(url: baseUrl.appendingPathComponent(path.rawValue), resolvingAgainstBaseURL: true)
            else {
                fatalError("Couldn't create URLComponents")
            }
        let request = URLRequest(url: components.url!)
        
        return apiRequest.run(request)
            .map(\.value)
            .eraseToAnyPublisher()
    }
}
