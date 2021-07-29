//
//  ComponentView.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/28.
//

import SwiftUI

/// View中需要用到的组件

// 自定义TextField
struct CustomTextField: View {
    
    var type: TextFieldType
    var placeholder: String
    @Binding var text: String
    
    @State var tap: Bool = false
    
    var body: some View {
        VStack {

            VStack {
                if type == .text {
                    TextField(placeholder, text: $text)
                        .autocapitalization(.none)
                } else {
                    SecureField(placeholder, text: $text)
                        .textContentType(.password)
                }
            }
            .font(tap ? /*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/ : .subheadline)
            .onTapGesture {
                withAnimation(.spring(response: 0.3, dampingFraction: 0.6, blendDuration: 0)) {
                    tap = true
                }
            }
            
            Divider()
        }
    }
}

// 登录（注册）按钮
struct SubmitButtonPart: View {
    
    @EnvironmentObject var AccountVM: AccountViewModel
    @State var loading: Bool = false
    
    var style: ButtonStyle
    var user: String
    var password: String
    
    init(style: ButtonStyle = ButtonStyle.Login, user: String, password: String) {
        self.loading = false
        self.user = user
        self.password = password
        self.style = style
    }
    
    var body: some View {
        
        // use navigation mode of rebeloper from youtube
        NavigationStep(
            type: .push,
            style: .view,
            isActive: style == .Login ? $AccountVM.LoginSucess : $AccountVM.SignUpSucess) {
            ContentView()
                .environmentObject(AccountVM)
        } label: {
            Button(action: {
                self.loading.toggle()
                if self.style == .Login {
                    AccountVM.Login(user: user, password: password)
                } else {
                    AccountVM.Signup(user: user, password: password)
                }
            }, label:
                {
                RoundedRectangle(cornerRadius: 10)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
                    .overlay(
                        ZStack {
                            if self.loading {
                                ProgressView()
                            } else {
                                Text(style.rawValue)
                            }
                        }
                        .foregroundColor(.white)
                    )
            })
            .onAppear {
                self.loading = false
            }
        } action: {
        }
        .disabled(!AccountVM.valid || self.loading)
        .padding(.top, 40)
    }
}

// 注销按钮
struct LogoutButtonPart: View {
    var body: some View {
        Button(action: {
            UIKitNavigation.popToRootView()
        }, label: {
            RoundedRectangle(cornerRadius: 10)
                .frame(maxWidth: UIScreen.main.bounds.width - 100)
                .frame(height: 50)
                .foregroundColor(Color.gray.opacity(0.3))
                .overlay(
                    Text("Logout")
                    .foregroundColor(.red)
                )
        })
    }
}

