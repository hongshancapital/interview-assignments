//
//  UserInfoModel.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/11.
//

import Foundation

struct UserInfoModel: Codable {
    let userId: String
    let token: String
    var userName: String?
    
    static private var shareUserModel: UserInfoModel!
    // 保存用户登录的信息
}

extension UserInfoModel {
    static func readCurrentUserModel() ->UserInfoModel?{
        shareUserModel
    }
    func saveCurrentUser()  {
        UserInfoModel.shareUserModel = self
    }
    static func logout() {
        shareUserModel = nil
    }
}
