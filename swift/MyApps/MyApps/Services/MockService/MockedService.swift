//
//  MockedService.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation
import Combine

class MockedAppInfoService: AppInfoServiceProtocol {
    func fetchMyApps(pageIndex: UInt, count: UInt) -> AnyPublisher<[AppInfo], Error> {
        return Just(AppInfo.mockedData).setFailureType(to: Error.self).eraseToAnyPublisher()
    }
    
    func updateAppCollectedSate(_ state: Bool, app: AppInfo) -> AnyPublisher<Bool, Error> {
        return Just(true).setFailureType(to: Error.self).eraseToAnyPublisher()
    }
    
    
}

