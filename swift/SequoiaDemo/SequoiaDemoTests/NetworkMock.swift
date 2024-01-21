//
//  URLProtocolMock.swift
//  SequoiaDemoTests
//
//  Created by 王浩沣 on 2023/5/7.
//

import Foundation
@testable import SequoiaDemo

import Combine

///mock网络数据回包
struct MockDataTaskPublisher : DataTaskPublisher {
    let mockData: Data
    let mockResponse: URLResponse
    func dataTaskPublisher(for url: URL) -> AnyPublisher<URLSession.DataTaskPublisher.Output , URLError> {
        return Result.success((data: mockData, response: mockResponse)).publisher.eraseToAnyPublisher()
    }
}

///mock网络回包错误
struct MockErrorTaskPublisher : DataTaskPublisher {
    let error: URLError
    func dataTaskPublisher(for url: URL) -> AnyPublisher<URLSession.DataTaskPublisher.Output , URLError> {
        return Result.failure(error).publisher.eraseToAnyPublisher()
    }
}
