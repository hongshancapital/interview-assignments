//
//  AppsAPI.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import Foundation

/// 应用相关接口
protocol AppsAPI {
    
    func fetchApps(params: Params) async -> ([AppModel]?, NetworkResponse)
    
    func likeApp(params: Params) async -> NetworkResponse
    
}
