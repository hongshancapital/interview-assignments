//
//  MockAppListService.swift
//  AppGalleryTests
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation
@testable import AppGallery

class MockAppListService: AppListService {
    
    var apps: [ChatApp]?
    
    var error: MockError?
    
    func fetchApps(limit: Int) async throws -> [ChatApp] {
        if let error = error {
            throw error
        }
        return Array(apps?.prefix(limit) ?? [])
    }
    
}

final class MockDelayAppListService: MockAppListService {
    
    override func fetchApps(limit: Int) async throws -> [ChatApp] {
        Thread.sleep(forTimeInterval: 4)
        return try await super.fetchApps(limit: limit)
    }
}
