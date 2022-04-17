//
//  URLSessionProtocol.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/17/22.
//

import Foundation

protocol URLSessionProtocol {
    func requestData(from url: URL) async throws -> (Data, URLResponse)
}

protocol DataLoaderProtocol {
    var onePageNum: Int { get set }
    func requestData(page: Int) async throws -> [AppItem]?
}
