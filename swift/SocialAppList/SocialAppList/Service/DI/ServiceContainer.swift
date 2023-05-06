//
//  ServiceContainer.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation

protocol ServiceContainer {}

extension ServiceContainer {
    var indonesService: IndonesServiceType { DI.resolve(IndonesServiceType.self)! }
}
