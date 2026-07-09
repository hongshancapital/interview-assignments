//
//  APIClient.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import Foundation

enum APIClientError: Error {
    case unknownUri
}

struct APIClient {
    
    enum Responder<T> {
        case response(T)
        case void
    }
    
    static func request<R: Requester>(_ r: R) async throws -> Responder<R.Response> {
        
        ///  这里硬编码只是用来配合mock，实际情况不会这么干，返回数据类型动态根据服务器返回的数据来解析类型
        if r.uri == MockURI.artistList.rawValue {
            let resut = try await MockApi.artistListRequest(r.params)
            return Responder<R.Response>.response(resut as! R.Response)
        }
        if r.uri == MockURI.artistDoLike.rawValue {
            try await MockApi.likeRequest(r.params)
            return .void
        }
        
        throw APIClientError.unknownUri
    }
    
}
