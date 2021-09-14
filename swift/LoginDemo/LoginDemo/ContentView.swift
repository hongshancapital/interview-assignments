//
//  ContentView.swift
//  LoginDemo
//
//  Created by 高丽军 on 2021/9/9.
//

import SwiftUI
let grayColor = UIColor(red: 214/255.0, green: 214/255.0, blue: 214/255.0, alpha: 1.0)

struct ContentView: View {
    @State var account: String = ""
    @State var password: String = ""
    @State var reviewPassword: String = ""
    @State var isLogin: Bool = true
    @State var errorMsg:String = ""
    @State var isShow:Bool = false
    
    var isCanLogin: Bool {
        account.count > 0 &&
        password.count > 8
    }
    
    var isCanCreate: Bool {
        isCanLogin && reviewPassword.count > 8
    }
    
    var ReviewView: some View {
        VStack {
            HStack {
                SecureField("Repart Password", text: $reviewPassword)
            }
            Divider()
        }
        .padding(.bottom,60)
    }
    
    var body: some View {
        NavigationView {
            VStack {
                HStack {
                    TextField("Name", text: $account)
                }
                Divider()
                HStack {
                    SecureField("Password", text: $password)
                }
                Divider()
                if !isLogin {ReviewView}
                if isLogin {
                    HStack(alignment: .center) {
                        Spacer()
                        Button(action: {
                            isLogin = false
                            errorMsg = ""
                        }, label: {
                            Text("Create Account").foregroundColor(Color(grayColor))
                        })
                    }
                    .padding(.bottom,60)
                }
                
                loginButton
                
                Text(errorMsg)
                .debugPrint(errorMsg)
                
                Spacer()
            }
            .padding(.top, 100)
            .padding(.leading)
            .padding(.trailing)
        }
        .navigationBarHidden(true)
    }
    
    var loginButton:some View {
        NavigationLink(destination: MainPageView(isShow: $isShow,loginName:$account),isActive:$isShow) {
            Button(action: {
                if isLogin {
                    login()
                }else {
                    createAccount()
                }
            }, label: {
                Text(isLogin ? "Login":"Create Account").foregroundColor(.white)
            })
            .frame(height: 50, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            .padding([.leading,.trailing],130)
            .background((isLogin ? isCanLogin:isCanCreate) ? Color.green:Color(grayColor))
            .cornerRadius(10.0)
        }
    }
    
    func createAccount() {
        if password != reviewPassword {
            errorMsg = "密码与验证码不一致"
            return
        }
        let res:HttpResponse = HttpRequest().createAccount(nickName: account, password: password)
        if res.requestCode == 200 {
            isLogin = true
            account = ""
            password = ""
            reviewPassword = ""
        }else {
            errorMsg = res.errorMessage!
        }
    }
    
    func login() {
        self.endEditing()
        let res:HttpResponse = HttpRequest().login(nickName: account, password: password)
        if res.requestCode == 200 {
            errorMsg = ""
            account = ""
            password = ""
            isShow = true
        }else {
            errorMsg = res.errorMessage!
        }
    }
    
    func endEditing() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

extension View {
    func debugPrint(_ value:Any) -> some View {
        #if DEBUG
        print(value)
        #endif
        return self
    }
}

