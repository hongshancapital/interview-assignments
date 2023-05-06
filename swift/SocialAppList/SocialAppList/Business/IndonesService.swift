//
//  IndonesService.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Moya
import Combine

protocol IndonesServiceType {
    func appList(pageSize: Int, index: Int) -> AnyPublisher<SocialAppResponse, MoyaError>
}

class IndonesService: IndonesServiceType, NetworkContainer {
    func appList(pageSize: Int, index: Int) -> AnyPublisher<SocialAppResponse, MoyaError> {
        // 真实网络请求
//        return networking.request(IndonesAPi.appList(pageSize: pageSize, index: index)).mapObject()
        
        // mock 数据
        return networking.request(
            IndonesAPi.appList(pageSize: pageSize, index: index),
            stubClosure: MoyaProvider.delayedStub(1)
        )
        .mapObject()
    }
}
