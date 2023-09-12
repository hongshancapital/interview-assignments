//
//  MockAppInfoListService.swift
//  AppHunterTests
//
//  Created by zhang shijie on 2023/5/24.
//

import Combine
import Foundation
struct MockAppInfoListService: AppInfoListServiceProtocol {
    let mockCollectAppInfo: AppInfo = .init(artworkUrl: "",
                                            trackName: "1",
                                            appDescription: "1", isCollected: false, trackId: 1_163_852_619)
    var jsonName: String = "res"
    func parse(jsonData: Data) -> AppInfoResponse? {
        do {
            let decodedData = try JSONDecoder().decode(AppInfoResponse.self,
                                                       from: jsonData)
            return decodedData

        } catch {
            return nil
        }
    }

    func loadJson() -> AppInfoResponse? {
        do {
            if let bundlePath = Bundle.main.path(forResource: jsonName,
                                                 ofType: "json"),
                let jsonData = try String(contentsOfFile: bundlePath).data(using: .utf8)
            {
                return parse(jsonData: jsonData)
            }
        } catch {
            return nil
        }
        return nil
    }

    func requestApps(from endpoint: AppInfoListEndpoint) -> AnyPublisher<AppInfoResponse, APIError> {
        guard let data = loadJson() else {
            return Fail(error: APIError.decodingError).eraseToAnyPublisher()
        }
        var curpage = 0
        switch endpoint {
        case let .appInfoList(page):
            curpage = page
        }
        let startIndex = curpage == 0 ? 0 : curpage * 10
        let endIndex = startIndex + 10
        var appInfoRes: AppInfoResponse?
        if let res = data.results {
            if let arrslice = res[safe: startIndex ..< endIndex] {
                appInfoRes = AppInfoResponse(resultCount: 10, results: Array(arrslice))
                if let appSlicedRes = appInfoRes {
                  return Just(appSlicedRes).setFailureType(to: APIError.self).eraseToAnyPublisher()
                }
            } else {
                appInfoRes = AppInfoResponse(resultCount: 0, results: nil)
                if let appSlicedRes = appInfoRes {
                    return Just(appSlicedRes).setFailureType(to: APIError.self).eraseToAnyPublisher()
                }
            }
        }
        return Fail(error: APIError.decodingError).eraseToAnyPublisher()
    }
}
