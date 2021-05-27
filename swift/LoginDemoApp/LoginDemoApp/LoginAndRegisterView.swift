//
//  LoginAndRegisterView.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI
import Combine

struct LoginAndRegisterView: View {
    
    @EnvironmentObject var userAuth: UserModel
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var repeatPassword: String = ""
    @State private var isDisabled = true
    @State private var showUserNameErrorAlert = false
    @State private var isRegister = false
    
    var body: some View {
        VStack(alignment: .leading, content:{
            TextField("Name", text: $username)
                .textFieldStyle(CommonTextField())
                .autocapitalization(.none)
                .onReceive(Just(username), perform: { _ in
                    onNameChanged(username)
                })
            Divider()
            SecureField("Password", text: $password)
                .textFieldStyle(CommonTextField())
                .onReceive(Just(password), perform: { _ in
                    onPasswordChanged(password)
                })
            if isRegister {
                Divider()
                SecureField("Repeat Password", text: $repeatPassword)
                    .textFieldStyle(CommonTextField())
                    .onReceive(Just(repeatPassword), perform: { _ in
                        onPasswordChanged(repeatPassword)
                    })
            }
            
            Divider()
            if !isRegister {
                HStack(content: {
                    Spacer()
                    Button(action: createAccount){
                        Text("Create Account").foregroundColor(.gray)
                    }
                }).padding(.top, 10)
            }
            
            Button(action: onConfirm) {
                Text(isRegister ? "Create Account" : "Login")
            }.padding(.top, 60)
            .disabled(isDisabled)
            .buttonStyle(GreenButton())
            Spacer()
        })
        .padding(30).padding(.top, 150)
        .alert(isPresented: $showUserNameErrorAlert, content: {
            Alert(title: Text("Tips"),
                  message: Text("invalidate username"),
                  dismissButton: .default(Text("ok")))
        })
    }
    
    func verifyUserNameAndPasswordLength() -> Bool {
        if isRegister {
            return username.isEmpty || password.count < 6 || repeatPassword.count < 6 || password != repeatPassword
        }
        return username.isEmpty || password.count < 6
    }
    
    func verifyUserName() -> Bool {
        return username.count >= 3 && username.count <= 12
    }
    
    func onNameChanged(_ name: String) {
        isDisabled = verifyUserNameAndPasswordLength()
    }
    
    func onPasswordChanged(_ password: String){
        isDisabled = verifyUserNameAndPasswordLength()
    }
    
    func resetToLogin() {
        // do register
        isRegister = false
        password = ""
        repeatPassword = ""
    }
    
    func onConfirm() {
        if !verifyUserName() {
            showUserNameErrorAlert = true
            return
        }
        
        if isRegister {
            resetToLogin()
        } else {
            // do login
            userAuth.login(username, password)
        }
    }
    
    func createAccount() {
        isRegister = true
    }
}

struct LoginAndRegisterView_Previews: PreviewProvider {
    static var previews: some View {
        LoginAndRegisterView()
    }
}
