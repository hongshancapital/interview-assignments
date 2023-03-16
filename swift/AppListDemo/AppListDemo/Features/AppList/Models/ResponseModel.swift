//
//  ResponseModel.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import Foundation

enum ResponseCode: Int, Codable {
    case success = 200
    case fail = 500
}

struct AppListResponseModel: Codable {
    let code: ResponseCode
    var appModels: [AppModel]
}

struct FavouriteResponseModel: Codable {
    let code: ResponseCode
}
