//
//  RequestManger.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
import UIKit

struct RequestManger {
    // 模拟下载
    
    static func requestBy(userName: String,passWord: String, finished: @escaping ((_ model: UserInfoModel?,_ error: Error?) -> Void)) {
        print("user name =\(userName),password \(passWord)")
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) { 

            let dict = ["userId": UUID().uuidString,"token": "token_id","userName":userName]
            let json = JSONDecoder()
            guard let data = try? JSONSerialization.data(withJSONObject: dict, options: .prettyPrinted) else{
                finished(nil,NSError(domain: "", code: 0, userInfo: nil))
                return
            }
            let tempModel = try? json.decode(UserInfoModel.self, from: data)
            tempModel?.saveCurrentUser()
            finished(tempModel,nil)
        }
    }
}


struct IPHONEDeviceInfo {
    
    static let textFieldHeight: CGFloat = 40
    
    static var navigateHeight: CGFloat {
        (44 + IPHONEDeviceInfo.statusBarHeight)
    }
 
    static var statusBarHeight: CGFloat {
   
        UIApplication.shared.windows.last?.windowScene?.statusBarManager?.statusBarFrame.height ?? 20
    }
    static var safeBottom: CGFloat {
        UIApplication.shared.windows.first?.safeAreaInsets.bottom ?? 0
                 
    }
    
    static var screenHeight: CGFloat {
        screenSize.height
    }
    static var screenWidth: CGFloat {
        screenSize.width
    }
    static var screenSize: CGSize {
        screenBounds.size
    }
    static var screenBounds: CGRect {
        UIScreen.main.bounds
    }
}
