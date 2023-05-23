//
//  RestfulWebRepository.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation
/// 仓库
protocol DemoRestfulRepository: RestfulWebRepository {}

struct DemoRealRestfulRepository: DemoRestfulRepository {
    let session: URLSession
    let baseURL: String
    let bgQueue = DispatchQueue(label: "bg_parse_queue")
    init(session: URLSession, baseURL: String) {
        self.session = session
        self.baseURL = baseURL
    }
}
