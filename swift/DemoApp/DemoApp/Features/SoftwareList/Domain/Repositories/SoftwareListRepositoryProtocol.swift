//
//  SoftwareListRepositoryProtocol.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import Foundation

protocol SoftwareListRepositoryProtocol: AnyObject {
    /// 获取软件信息
    /// - Parameters:
    ///   - count: 获取的数量
    ///   - completionHandler: 完成后的回调，包含了获取的数据
    func getSoftware(count: Int) async -> Result<[Software], Failure>
}
