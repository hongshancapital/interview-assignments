//
//  DataLoaderMock.swift
//  SwiftUIHWTests
//
//  Created by 施治昂 on 4/17/22.
//

@testable import SwiftUIHW

final class DataLoaderMock: DataLoaderProtocol {
    func requestData(page: Int) async throws -> [AppItem]? {
        try await Task.sleep(nanoseconds: 500_000_000)
        return nil
    }
    var onePageNum: Int = 10
}
