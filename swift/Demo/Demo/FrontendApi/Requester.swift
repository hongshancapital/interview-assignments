//
//  Requester.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import Foundation

protocol Requester {
    associatedtype Response: Decodable
    
    var uri: String { get }
    var params: [String: Any] { get }
}

struct VoidResponse: Decodable {}

struct ArtistListRequest: Requester {
    typealias Response = [ArtistModel]
    
    let page: Int
    let pageSie: Int
    
    var uri: String {
        return MockURI.artistList.rawValue
    }
    
    var params: [String : Any] {
        [
            "page": page,
            "pageSize": pageSie
        ]
    }
}

struct ArtistLikeRequest: Requester {
    typealias Response = VoidResponse
    
    let isLike: Bool
    let trackId: Int
    
    var uri: String {
        return MockURI.artistDoLike.rawValue
    }
    
    var params: [String : Any] {
        [
            "isLike": isLike,
            "trackId": trackId
        ]
    }
}
