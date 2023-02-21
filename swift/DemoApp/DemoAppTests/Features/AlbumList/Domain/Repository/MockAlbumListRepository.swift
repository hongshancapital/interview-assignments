//
//  MockSoftwareListRepository.swift
//  DemoAppTests
//
//  Created by 黄瑞 on 2023/2/16.
//

import Foundation
@testable import DemoApp

//MARK: - Mock Repository
class MockSoftwareListRepository: SoftwareListRepositoryProtocol {
    /// 是否调用了该方法
    var isGetSoftwareCalled = false
    /// mock 返回值
    var getSoftwareResult: Result<[DemoApp.Software], DemoApp.Failure>?
    func getSoftware(count: Int) async -> Result<[Software], Failure> {
        isGetSoftwareCalled = true
        if let result = self.getSoftwareResult {
            return result
        } else {
            return .failure(Failure())
        }
    }
}
