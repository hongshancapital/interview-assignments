//
//  MockAppsModel.swift
//  AppsDemoTests
//
//  Created by changcun on 2022/3/19.
//

import Foundation
@testable import AppsDemo

final class MockAppsDataService: AppsDataServiceProtocol {
    var error: Error?
    var apps: [AppInfoModel] = []
    
    func fetchApps(page: Int) async throws -> [AppInfoModel]? {
        guard error == nil else {
            throw error!
        }
        
        return apps
    }
}
