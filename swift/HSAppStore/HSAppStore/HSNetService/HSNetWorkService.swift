//
//  HSNetWorkService.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/25.
//

import Foundation

enum HSNetWorkServiceStatus: Error {
    case serviceRequestError
}

class HSNetWorkService {
    static let shared = HSNetWorkService()
    
    func requestAppService(from requestAPI: String, with requestParams: HSNetworkParams) async throws -> HSAppDataSource? {
        do {
            let appData: HSAppDataSource = try await HSNetWork.shared.requestAppData(from: requestAPI, params: requestParams)
            return appData
        } catch {
            throw HSNetWorkServiceStatus.serviceRequestError
        }
    }
}
