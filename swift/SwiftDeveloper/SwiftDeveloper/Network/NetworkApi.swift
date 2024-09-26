//
//  NetworkApi.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/18.
//

import Foundation

class NetworkApi {
    static let shared = NetworkApi()

    private var appsModel: AppsModel?

    init() {
        appsModel = FileLoader.loadAppsDataFromFilename("data")
    }

    func fetchApps(offset: Int,
                   limit: Int,
                   onSuccess: @escaping (_ appsModel: AppsModel) -> Void,
                   onError: @escaping (_ errorMessage: String) -> Void)
    {
        let seconds = 1.0

        DispatchQueue.main.asyncAfter(deadline: .now() + seconds) { [weak self] in
            if let appsModel = self?.appsModel {
                if offset >= appsModel.results.count {
                    let appsFetched = AppsModel(resultCount: appsModel.resultCount,
                                                results: [])
                    onSuccess(appsFetched)
                } else {
                    let lastIndex = (offset + limit) < appsModel.resultCount ?
                        offset + limit :
                        appsModel.resultCount - 1
                    let resultsFetched = Array(appsModel.results[offset ... lastIndex])
                    let appsFetched = AppsModel(resultCount: appsModel.resultCount,
                                                results: resultsFetched)
                    onSuccess(appsFetched)
                }
            } else {
                onError("no data currently")
            }
        }
    }
}
