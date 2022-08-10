//
//  API.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import Foundation

struct APIManager {
    public static func fetchApplicationData(pagination: Pagination) async -> [Application]? {
        // Do the request
        var mockData: [ApplicationModel]
        do {
            mockData = try await MockAPI.getMockApplicationList(pagination: pagination) ?? []
        } catch {
            mockData = []
        }
        let applications = mockData.map { model in
            return Application(from: model)
        }
        return applications
    }
}
