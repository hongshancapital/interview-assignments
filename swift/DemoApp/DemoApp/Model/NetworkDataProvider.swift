//
//  NetworkDataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/20.
//

import Foundation

class NetworkDataProvider: DataProvider {
    // TODO: 负责向服务端请求数据
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?) -> Void) {
        // stub
    }
}
