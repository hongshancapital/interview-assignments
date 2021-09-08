//
//  CreateView.swift
//  SwiftUIDemo
//
//  Created by 齐风修 on 2021/9/7.
//

import SwiftUI

struct CreateView: View {
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var rePassword: String = ""
    @State private var presentToMain = false

    var body: some View {
        NavigationView {
            VStack {
                TextField(
                    "Name",
                    text: $username
                ) { _ in
                } onCommit: {
                }
                .font(.body)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))
                
                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
                
                SecureField(
                    "Password",
                    text: $password
                )
                .font(.body)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))
                
                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
                
                SecureField(
                    "Repeat Password",
                    text: $rePassword
                )
                .font(.body)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))
                
                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 16, trailing: 16))
                
                Button.init(action: {
                    if rePassword != password {
                        return
                    }
                    if password.count < 8 || username.isEmpty {
                        return
                    }
                    presentToMain.toggle()
                }) {
                    Text("Create Accout")
                        .font(.body)
                        .foregroundColor(Color.white)
                        .cornerRadius(8)
                }
                .padding(EdgeInsets(top: 16, leading: 32, bottom: 16, trailing: 32))
                .frame(width:350, height: 44, alignment: .center)
                .background((username.isEmpty || password.count < 8 || rePassword != password) ? grayColor : greenColor)
                .cornerRadius(22)
                
            }
            
        }.fullScreenCover(isPresented: $presentToMain, content: {
            MainView(username: username)
        })
    }
    
}

struct CreateView_Previews: PreviewProvider {
    static var previews: some View {
        CreateView()
    }
}
