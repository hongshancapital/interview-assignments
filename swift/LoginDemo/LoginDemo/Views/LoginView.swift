//
//  LoginView.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import SwiftUI

struct LoginView: View {

    @StateObject var AccountVM = AccountViewModel(style: .Login)
    @State var username: String = ""
    @State var password: String = ""

    var body: some View {
        
        NavigationView {
            VStack(alignment: .leading, spacing: 20.0) {
                
                // User Name
                CustomTextField(type: .text, placeholder: "Name", text: $AccountVM.name)
  
                // Password
                CustomTextField(type: .password, placeholder: "Password", text: $AccountVM.password)
     
                VStack {
                    // Create Acccount Row
                    TipandCreateRowPart
                    
                    // Login Row
                    SubmitButtonPart(style: .Login, user: username, password: password)
                }
                .environmentObject(AccountVM)
                
                Spacer()
            }
            .frame(width: UIScreen.main.bounds.width - 80)
            .padding(.top, 40)
            .navigationTitle("Login")
        }
    }
}

struct CreateAccountView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}

/// 包含错误提示文本和"Create Account"跳转链接
/// ```
/// 使用extension避免多个@State参数传输
/// Modify at 07.30.2021 @Chr1s78
///
/// ```
extension LoginView {
    
    var TipandCreateRowPart: some View {
        HStack {
            Text(AccountVM.password_err_tip == "" ? AccountVM.name_err_tip : AccountVM.password_err_tip)
                .font(.footnote)
                .foregroundColor(.red)
            Spacer()
            
            // use navigation mode of rebeloper from youtube
            NavigationStep(type: .push, style: .view) {
                SignupView()
            } label: {
                Text("Create Account")
                    .foregroundColor(.gray)
            }
        }
    }
}

