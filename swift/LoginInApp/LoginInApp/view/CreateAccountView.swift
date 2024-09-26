//
//  CreateAccountView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
import SwiftUI

// 创建账号 视图
struct CreateAccountView: View {
    
    @Binding var isShowRootView: Bool
    @Binding var loginSuccess: Bool
    
    
    @State private var userName = ""
    @State private var password = ""
    @State private var rePassword = ""
    @State private var disabled = true
    
    //    @State private var showAlert = false
    @State private var alertTitle = ""
    
    @State var isRunIndicator = true
    @State var title = "正在创建账号"
    @State var isShowLoading = false
    
    @State var pointY: CGFloat = -100
    
    var body: some View {
        ZStack(content: {
            VStack(content: {
                mainView
                if isShowLoading {
                    NetworkIndicatorView(run: $isRunIndicator, title: $title).offset(x: 0, y: -IPHONEDeviceInfo.screenHeight/2)
                }
            })
            alertView
        })
        
    }
    
    var alertView: some View {
        ShowAlertView(message: $alertTitle).position(x: IPHONEDeviceInfo.screenWidth/2, y:pointY).animation(animation)
    }
    
    var animation: Animation {
        Animation.spring(response: 0.5, dampingFraction: 0.6, blendDuration: 0.1)
    }
    
    var mainView: some View {
        
        ScrollView(.vertical, showsIndicators: false, content: {
            VStack(content: {
                VStack(alignment: .trailing, content: {
                    
                    UserNameTextField(userName: $userName) { (value: String) in
                        
                        if value.count > 1 && password.count > 8 && rePassword.count > 8 {
                            disabled = false
                        }else {
                            disabled = true
                        }
                        
                    } 
                    
                    UserPassWordTextField(passWord: $password) { (value: String) in
                        
                        if value.count > 8 && userName.count > 1 && rePassword.count > 8 {
                            disabled = false
                        }else {
                            disabled = true
                        }
                        
                    } 
                    
                    UserPassWordTextField(passWord: $rePassword,placeText: "configPassword".localizedValue) { (value: String) in
                        if value.count > 8 && userName.count > 1 && password.count > 8 {
                            disabled = false
                        }else {
                            disabled = true
                        }
                    } 
                    
                }).padding(EdgeInsets(top: IPHONEDeviceInfo.navigateHeight, leading: 40, bottom: 40, trailing: 40))
                
                ActionButton(buttonTitle: "createAccount".localizedValue, disabled: $disabled) { 
                    dismissKeyBorder()
                    judgePassWord()
                }
                Spacer()
            }).frame(height: UIScreen.main.bounds.height - IPHONEDeviceInfo.navigateHeight, alignment: .center).background(Color(UIColor.systemBackground)).hiddenKeyBorderView()
        }).navigationBarTitle(Text("createAccount".localizedValue), displayMode: .inline)
    }
    
    
}
extension CreateAccountView {
    
    func judgePassWord() {
        
        
        if !DataVerificationManger.isValidUser(userName: userName) {
            showAlertView(message: "用户名不符合规则")
            return
        }
        
        if rePassword != password {
            showAlertView(message: "两次输入的密码不一样")
            return
        }
        if !DataVerificationManger.isVaildPassWord(password: password) {
            showAlertView(message: "密码长度为9-12位,不能有特殊字符")
            return
        }
        
        isShowLoading = true
        RequestManger.requestBy(userName: userName, passWord: password) { (data, error) in
            isShowLoading = false
            if error == nil {
                loginSuccess = true
                isShowRootView.toggle()
            }else {
                loginSuccess = false
            }
        }
    }
    
    func showAlertView(message: String) {
        alertTitle = message
        pointY = IPHONEDeviceInfo.navigateHeight + 20
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) { 
            pointY = -100;
        }
    }
}

struct CreateAccountView_Previews: PreviewProvider {
    static var previews: some View {
        CreateAccountView(isShowRootView: .init(get: { () -> Bool in
            true
        }, set: { (newValue) in
            
        }), loginSuccess: .init(get: { () -> Bool in
            true
        }, set: { (newValue) in
            
        }))
    }
}

