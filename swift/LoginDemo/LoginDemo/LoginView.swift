//
//  LoginView.swift
//  LoginDemo
//
//  Created by MC on 2021/4/9.
//

import SwiftUI

/// 省略了全局状态，把用户名密码保存在全局中，用@EnvironmentObject访问
struct LoginView: View {
    @StateObject var viewModel = LoginViewModel()
    
    private var loginButton: some View {
        NavigationLink(
            destination: UserView(),
            isActive: $viewModel.loginSuccessed,
            label: {
                GeometryReader { reader in
                    Button(action: {
                        viewModel.login()
                    }) {
                        Text(viewModel.type == .login ? "Login" : "Create Account")
                            .foregroundColor(.white)
                            .frame(width: reader.size.width, height: reader.size.height)
                            .background(viewModel.loginBtnEnable ? Color.green : Color.secondary)
                            .cornerRadius(6)
                    }
                    
                }
                
                .frame(height: 40)
            })
            .disabled(!viewModel.loginBtnEnable)
    }
    
    private var createAccountButton: some View {
        HStack {
            Spacer()
            
            Button("Create Account") {
                viewModel.type = .createAccount
                viewModel.repeatPassword = ""
            }
        }
        .frame(height: 40)
    }
    
    var body: some View {
        VStack {
            InputView(placeholder: "Name", type: .username, text: $viewModel.userName)
            
            InputView(placeholder: "Password", type: .password, text: $viewModel.password)
            
            if viewModel.type == .createAccount {
                InputView(placeholder: "Repeat Password", type: .password, text: $viewModel.repeatPassword)
            } else {
                createAccountButton
            }
            
            loginButton
        }
        .padding(.horizontal, 20)
        .animation(.easeInOut)
        .navigationBarHidden(true)
        .ignoresSafeArea()
    }
}

enum InputViewType {
    case username
    case password
}

struct InputView: View {
    let placeholder: String
    let type: InputViewType
    @Binding var text: String
    
    var body: some View {
        VStack(spacing: 0) {
            Group {
                if type == .username {
                    TextField(placeholder, text: $text)
                        .textContentType(.username)
                } else {
                    SecureField(placeholder, text: $text)
                        .textContentType(.password)
                }
            }
            .frame(height: 50)

            Divider()
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
