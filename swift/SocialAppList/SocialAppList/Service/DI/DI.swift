//
//  DI.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Swinject
import Then

extension Container: Then {}

let DI = Container().then {
    // MARK: Network

    $0.register(Network.self) { _ in Network() }.inObjectScope(.container)

    // MARK: Service
    $0.register(IndonesServiceType.self) { _ in IndonesService() }.inObjectScope(.container)
}
