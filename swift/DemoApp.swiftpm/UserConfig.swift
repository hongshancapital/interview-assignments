//
//  UserConfig.swift
//  DemoApp
//
//  Created by 黄磊 on 2022/4/11.
//


import Foundation
import MJModule

final class UserConfig: ConfigProtocol {
    static var configs: [String : Any] = [
    
        ConfigKey.kAppId        : "1515090139",
        ConfigKey.kServerBaseHost : "http://127.0.0.1",
        ConfigKey.kServerPath: "/DemoApp/",
        
        ConfigKey.Log.level : LogLevel.info,
    ]
}

