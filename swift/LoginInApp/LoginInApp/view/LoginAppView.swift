//
//  LoginAppView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/10.
//

import SwiftUI

// 登录 视图
struct LoginAppView: View {
    
    @Binding var isShowRootView: Bool
    @Binding var loginSuccess: Bool
    
    var isPushView = true
    
    @State private var userName = ""
    @State private var password = ""
    @State private var disabled = true
    @State private var isShowCreateAcount = false
    
    @State var isRunIndicator = true
    @State var title = "正在登录"
    @State var isShowLoading = false
    @State var isDisabled = false
    
    @State var offSetY: CGFloat = 0
    
    @State private var alertTitle = ""
    @State private var pointY: CGFloat = -100
    
    
    var body: some View {
        ZStack(content: {
            VStack {
                if isPushView {
                    mainView
                }else {
                    NavigationView(content: {
                        mainView
                    })
                    
                }
                if isShowLoading {
                    NetworkIndicatorView(run: $isRunIndicator, title: $title).offset(x: 0, y: -IPHONEDeviceInfo.screenHeight/2)
                }
                
            }.disabled(isDisabled).background(Color(UIColor.systemBackground))
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
            textFeildView
        })
    }
    
    
    
    var textFeildView: some View {
        VStack(content: {
            VStack(alignment: .trailing, content: {
                
                UserNameTextField(userName: $userName) { (value: String) in
                    if value.count > 1 && password.count > 8 {
                        disabled = false
                    }else {
                        disabled = true
                    }
                } 
                
                UserPassWordTextField(passWord: $password) { (value: String) in
                    if value.count > 8 && userName.count > 1 {
                        disabled = false
                    }else {
                        disabled = true
                    }
                } 
                
                
                NavigationLink("createAccount".localizedValue, destination: CreateAccountView(isShowRootView: $isShowRootView, loginSuccess: $loginSuccess), isActive: $isShowCreateAcount).font(Font.system(size: 12)).foregroundColor(.gray)
                
            }).padding(EdgeInsets(top: IPHONEDeviceInfo.navigateHeight, leading: 40, bottom: 40, trailing: 40))
            
            ActionButton(buttonTitle: "login".localizedValue, disabled: $disabled) { 
                dismissKeyBorder()
                handleRequestURL()
            }
            Spacer()
        }).frame(height: UIScreen.main.bounds.height - IPHONEDeviceInfo.navigateHeight, alignment: .center).background(Color(UIColor.systemBackground)).hiddenKeyBorderView().navigationBarTitle(Text("login".localizedValue), displayMode: .inline)
    }
    
    
}

extension LoginAppView {
    
    func showAlertView(message: String) {
        alertTitle = message
        pointY = IPHONEDeviceInfo.navigateHeight + 20
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) { 
            pointY = -100;
        }
    }
    
    func handleRequestURL() {
        
        if !DataVerificationManger.isValidUser(userName: userName) {
            showAlertView(message: "用户名不符合规则")
            return
        }
        if !DataVerificationManger.isVaildPassWord(password: password) {
            showAlertView(message: "密码不符合规则")
            return
        }
        
        isShowLoading = true
        isDisabled = true
        
        RequestManger.requestBy(userName: userName, passWord: password) { (data, error) in
            isShowLoading = false
            if error == nil {
                print("登陆成功")
                isRunIndicator = false
                loginSuccess = true
                isShowRootView.toggle()
            }else {
                loginSuccess = false
                isDisabled = false
                print("显示错误信息")
            }
        }
    }
}

struct LoginAppView_Previews: PreviewProvider {
    static var previews: some View {
        LoginAppView(isShowRootView: .init(get: { () -> Bool in
            true
        }, set: { (newValue) in
            
        }),loginSuccess: .init(get: { () -> Bool in
            true
        }, set: { (newValue) in
            
        }))
    }
}
