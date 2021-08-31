//
//  ContentView.swift
//  Login
//
//  Created by xiwang wang on 2021/8/30.
//

import SwiftUI

struct LoginView: View {
    
    @State private var presentedRegister = false
    @State private var presentedHome = false
//    @State private var canLogin = false
    /// 输入表单规则校验
    // 用户名
    @ObservedObject private var validateName: ValidatedValue<String> = ValidatedValue(
        "",
        validator: { current, new in
            return new
        }
    )
    
    // 密码
    @ObservedObject private var validatePassword: ValidatedValue<String> = ValidatedValue(
        "",
        validator: { current, new in
            return new
        }
    )
    
    var body: some View {
        VStack(alignment: .center){
            VStack(alignment: .center){
                LoginTextFieldView(string: $validateName.value, type: .userName)
                VStack(alignment: .trailing){
                    LoginTextFieldView(string: $validatePassword.value, type: .password)
                    Button(action: {
                        presentedRegister.toggle()
                    }, label: {
                        Text("Create Account")
                            .foregroundColor(.gray)
                            .font(.footnote)
                            .padding(EdgeInsets(top: 5, leading: 0, bottom: 0, trailing: 25))
                    })
                    //
                    .fullScreenCover(isPresented: $presentedRegister, content: {
                        RegisterView()
                    })
                    
//                    .sheet(isPresented: $presentedRegister, content: {
//                        RegisterView()
//                    })
                }
                
//                ConfirmButton(title: "Login", action: {
//                    canLogin.toggle()
//                }, isNomal: $canLogin)
                
                Button(action: {
                    presentedHome = validateName.value.count > 0 && validatePassword.value.count > 8
                }, label: {
                    Rectangle()
                        .fill(validateName.value.count > 0 && validatePassword.value.count > 8 ? Color("themeGreen"): Color("disableGray"))
                        .frame(height: 44, alignment: .center)
                        .overlay(Text("Login").foregroundColor(.white).bold())
                        .cornerRadius(8)
                        .padding(EdgeInsets(top: 25, leading: 30, bottom: 0, trailing: 30))
                })
                .fullScreenCover(isPresented: $presentedHome, content: {
                    HomeView()
                })
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    //    @State static var myName = ""
    //    @State static var myPassword = ""
    static var previews: some View {
        LoginView()
    }
}
