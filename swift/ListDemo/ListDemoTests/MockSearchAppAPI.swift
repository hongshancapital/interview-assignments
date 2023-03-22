//
//  MockSearchAppAPI.swift
//  ListDemoTests
//
//  Created by kent.sun on 2023/2/3.
//

import Foundation
import Combine
import Alamofire
@testable import ListDemo

class MockSearchAppAPI: SearchAppAPIProtocol {
    
    var mockRepos: DataModel.AppItems {
        let appItem = DataModel.AppItem(artworkUrl100: "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/19/91/17/199117a1-e4ab-8990-4934-74cb99662f26/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/100x100bb.jpg", description: "Google Chat is an intelligent and secure communication", trackId: 1163852619, title: "Google Chat")
        let data = DataModel.AppItems.init(resultCount: 1, results: [appItem])
        return data
    }
    
    // confirming to protocol returning mock responsse
    func searchApp<T>(quary: String, limit: Int, offset: Int) -> AnyPublisher<T, AFError> {
        if T.self == DataModel.AppItems.self {
            return Just(mockRepos as! T)
                .setFailureType(to: AFError.self)
                .eraseToAnyPublisher()
        }
       
        return Fail(error: AFError.sessionDeinitialized).eraseToAnyPublisher()
    }
}
