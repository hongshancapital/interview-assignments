//
//  MockURLProtocol.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/9.
//

import Foundation

class MockURLProtocol: URLProtocol {
    static var mockResponses : [String: String] = ["/feeds" : "feeds.txt"]
    static let MockArguments = "--use-mock-data"
    
    override class func canInit(with task: URLSessionTask) -> Bool {
        return task.currentRequest?.url?.host() == NetworkManager.API_HOST
    }
    
    override class func canInit(with request: URLRequest) -> Bool {
        return request.url?.host() == NetworkManager.API_HOST 
    }
    
    override class func canonicalRequest(for request: URLRequest) -> URLRequest {
        return request
    }
    
    override func startLoading() {
        guard let fileName = MockURLProtocol.mockResponses[request.url!.path] else {
            return
        }
        
        let bundle = Bundle(for: type(of: self))
        let url = bundle.url(forResource: fileName, withExtension: nil)!
        let data = try! Data(contentsOf: url)
        
        // mock delay 2 seconds
        sleep(1)
        
        let response = HTTPURLResponse(url: request.url!, statusCode: 200, httpVersion: nil, headerFields: nil)!
        client?.urlProtocol(self, didReceive: response, cacheStoragePolicy: .notAllowed)
        client?.urlProtocol(self, didLoad: data)
        client?.urlProtocolDidFinishLoading(self)
        
    }
    
    override func stopLoading() { }
}
