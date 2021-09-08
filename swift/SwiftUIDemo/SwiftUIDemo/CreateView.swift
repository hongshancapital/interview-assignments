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
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))
                
                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))

                SecureField(
                    "Password",
                    text: $password
                )
                .font(.body)
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))

                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
//
                SecureField(
                    "Repeat Password",
                    text: $rePassword
                )
                .font(.body)
                .padding(EdgeInsets(top: 0, leading: 32, bottom: 0, trailing: 32))

                Divider().padding(EdgeInsets(top: 0, leading: 16, bottom: 16, trailing: 16))

                HStack {
                    Button.init(action: {
                        if rePassword != password || password.count < 8 || username.isEmpty {
                            return
                        }
                        presentToMain.toggle()
                    }) {
                        HStack {
                            Text("Create Accout")
                                .font(.title)
                                .foregroundColor(Color.white)
                        }
                        .frame(minWidth: 0, maxWidth: .infinity, maxHeight: 44, alignment: .center)
                        .foregroundColor(.white)
                        .background((username.isEmpty || password.count < 8 || rePassword != password) ? grayColor : greenColor)
                        .cornerRadius(22)
                    }
                }.padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
            }
            
        }
        .fullScreenCover(isPresented: $presentToMain, content: {
            MainView(username: username)
        })
    }
    
}

struct CreateView_Previews: PreviewProvider {
    static var previews: some View {
        CreateView()
    }
}
