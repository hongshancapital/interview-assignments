//
//  UserModuleManager.swift
//  DemoApp
//
//  Created by 黄磊 on 2022/4/12.
//

import MJModule

class UserModuleManager: ModuleManageable {
    static var blockAutoModuleSearch: Bool = true
    
    static var registerModules: [ModuleProtocol.Type] = [
        MockNetworking.self
    ]

    static func registerFunction() {
    }
}
