//
//  ContentView.swift
//  SwiftUIDemo
//
//  Created by 齐风修 on 2021/9/6.
//

let greenColor = Color.init(red: 108/255.0, green: 180/255.0, blue: 97/255.0)
let grayColor = Color.init(white: 215/255.0)

import SwiftUI

struct LoginView: View {
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var presentToCreate = false
    @State private var presentToMain = false
    
    var body: some View {
        NavigationView{
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
                
                HStack {
                    Spacer()
                    Button.init(action: {
                        presentToCreate.toggle()
                    }, label: {
                        Text("Create Account").font(.body).foregroundColor(.gray)
                    }).padding()
                }
                Button.init(action: {
                    if (username.isEmpty || password.count < 8) {
                        return
                    }
                    presentToMain.toggle()
                }) {
                    Text("Login")
                        .font(.body)
                        .foregroundColor(Color.white)
                }
                .padding(EdgeInsets(top: 16, leading: 32, bottom: 16, trailing: 32))
                .frame(width:350, height: 44, alignment: .center)
                .background((username.isEmpty || password.count < 8) ? grayColor : greenColor)
                .cornerRadius(22)
                
                
            }
            
        }.navigationBarTitle("首页", displayMode: .inline)
        .fullScreenCover(isPresented: $presentToCreate, content: {
            CreateView()
        })
        .fullScreenCover(isPresented: $presentToMain, content: {
            MainView(username: username)
        })
        
    }
    
    func goToCteate() {
        
    }
    
    func doLogin() {
        
    }
}



struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
