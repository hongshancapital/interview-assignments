//
//  HomeViewModel.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import UIKit

class HomeViewModel: NSObject {
    //退出登录
    func logout(result: @escaping (_ success:Bool,_ tip:String)->(Void)) {
        NetWorkManager.shared.request(urlString: LogoutUrl, methodType: .POST, parameters: [:]) { responseModel in
            CurrentUser = nil
            result(true,"Logout successfully!")
        }
    }
}
