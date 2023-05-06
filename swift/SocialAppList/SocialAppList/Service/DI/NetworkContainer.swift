//
//  NetworkContainer.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation

protocol NetworkContainer {}

extension NetworkContainer {
    var networking: Network { return DI.resolve(Network.self)! }
}
