//
//  APIService.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import Foundation

struct APIService {

    static let shared = APIService()

    private init() {}
}

extension APIService {

    enum APIError: Error {
        case noResponse
        case parameterError
    }

    enum Endpoint {
        case appList(page: Int, count: Int = 20)
    }
}

// MARK: - mock
extension APIService {

    func mockData<T: Decodable>() async -> T? {
        try? await Task.sleep(nanoseconds: 2 * 1_000_000_000)

        let path = Bundle.main.url(forResource: "data.json", withExtension: nil)
        guard let path, let data = try? Data(contentsOf: path) else {
            return nil
        }
        let decoder = JSONDecoder()
        let result = try? decoder.decode(T.self, from: data)
        return result
    }

    func mockRequest(_ endpoint: Endpoint) async -> Result<AppListModel, APIError> {
        let data: MockData? = await mockData()
        guard let data else {
            return .failure(.noResponse)
        }

        switch endpoint {
        case let .appList(page: page, count: count):
            guard page > 0, count > 0 else {
                return .failure(.parameterError)
            }
            let max = data.appModels.count

            // start
            let start = (page - 1) * count
            if start > max {
                let empty = AppListModel(total: data.total, currentPage: page, appModels: Array())
                return .success(empty)
            }

            // end
            var end = start + count
            end = end > max ? max : end
            let appModels = data.appModels[start..<end]

            let appListModel = AppListModel(total: data.total, currentPage: page, appModels: Array(appModels))
            return .success(appListModel)
        }
    }
}
