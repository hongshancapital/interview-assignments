    //
    //  ApiService.swift
    //  DemoApp
    //
    //  Created by Gao on 2022/7/11.
    //

import Foundation
import Combine
import SwiftUI

class ApiService {
      // 获取列表数据
    func getListData() -> AnyPublisher<[AppModel], Error> {
        return Future<[AppModel], Error> { promise in
            promise(.success(ModelData().appListData))
        }.eraseToAnyPublisher()
    }
    
}
