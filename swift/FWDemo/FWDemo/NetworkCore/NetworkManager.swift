//
//  NetworkManager.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation

enum Method {
    case get
    case post
}

class NetworkManager {
    static let shared = NetworkManager()
    
    func sendRequest(requestType : RequestType) -> URLSession.DataTaskPublisher {
        let urlRequest = self.setupRequest(requestType)
        return URLSession.shared.dataTaskPublisher(for: urlRequest)
    }
    
    private func setupRequest(_ requestType : RequestType) -> URLRequest {
        var url = requestType.baseURL
        
        if let path = requestType.path {
            url = URL(string: path, relativeTo: url) ?? url
        }
        
        var urlRequest = URLRequest(url: url)
        if let param = requestType.param {
            
            if requestType.method == .get {
                let query = param.map { (key: String, value: String) in
                    return "\(key)=\(value)"
                }.joined(separator: "&")
                url = URL(string: url.absoluteString.appending("?\(query)")) ?? url
                urlRequest = URLRequest(url: url)
                urlRequest.httpMethod = "GET"
                
            }else if requestType.method == .post {
                ///
            }
        }
        
        return urlRequest
    }
}
