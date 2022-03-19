//
//  AppInfoBaseModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import Foundation
import SwiftUI

protocol AppsDataServiceProtocol {
    /// 加载数据
    func fetchApps(page: Int) async throws -> [AppInfoModel]?
}

@available(iOS 15.0, *)
struct AppsDataService: AppsDataServiceProtocol {
    func fetchApps(page: Int) async throws -> [AppInfoModel]? {
        guard page > 0 else {
            throw NetworkError.customer(errorMessage: "page must > 0")
        }
        
        do {
            let response: ResponseModel<[AppInfoModel]> = try LoadBundleDataService().loadJsonFromBundle("apps.json")
            
            return response.results
        } catch {
            throw NetworkError.customer(errorMessage: "Load data error.")
        }
    }
}
