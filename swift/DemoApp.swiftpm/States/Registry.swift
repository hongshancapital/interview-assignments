//
//  Registry.swift
//  
//
//  Created by 黄磊 on 2022/4/11.
//

import Foundation
import UIKit
import MJModule

final class Registry {
    
    static var shared = Registry()
    
    func startRetisteAfterLaunch(
        _ application: UIApplication,
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) {
        // 注册通用 JSON 编码/解码器
        let encoder = JSONEncoder()
        encoder.dateEncodingStrategy = .iso8601
        JsonEncodeWrapper.encoder = encoder
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .iso8601
        JsonDecodeWrapper.decoder = decoder
    }
}
