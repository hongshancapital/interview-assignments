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
            destination: Text("Destination"),
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
                    .disabled(!viewModel.loginBtnEnable)
                }
                .frame(height: 40)
            })
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
            InputView(placeholder: "Name", text: $viewModel.userName)
            
            InputView(placeholder: "Password", text: $viewModel.password)
            
            if viewModel.type == .createAccount {
                InputView(placeholder: "Repeat Password", text: $viewModel.repeatPassword)
            } else {
                createAccountButton
            }
            
            loginButton
        }
        .padding(.horizontal, 20)
        .animation(.easeInOut)
    }
}

struct InputView: View {
    let placeholder: String
    @Binding var text: String
    
    var body: some View {
        VStack(spacing: 0) {
            TextField(placeholder, text: $text)
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
