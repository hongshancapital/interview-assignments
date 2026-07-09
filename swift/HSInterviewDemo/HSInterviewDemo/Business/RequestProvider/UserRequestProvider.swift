//
//  UserRequestProvider.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService

class UserRequestProvider: MKAPIRequest<[User]> {

    override var path: String {
        return "/api/infomation/list"
    }

    deinit {
        debugPrint("LaunchAdRequest deinit")
    }
}
