//
//  RequestDataService.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/10.
//

import Foundation

enum RequestError: Error {
    case requestFail
}

protocol RequestDataService {
    func fetchAppList(atPage page: Int, pageCount: Int) async throws -> AppListResponseModel
    func toggleFavouriteApp(_ appModel: AppModel) async throws -> FavouriteResponseModel
}
