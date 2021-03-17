//
//  LoginView.swift
//  SwiftUIHomeWork
//
//  Created by RemiliaScarlet on 2021/3/15.
//

import SwiftUI

struct LoginView: View {
    
    //@ObservedObject var userInfo = MockUserInfo()
    @EnvironmentObject var userInfo:MockUserInfo
    
    @State var userName:String = ""
    @State var userPwd:String = ""
    @State var isCreateAccount = false
    @State var isLoginSuccess = false
    @State var isUsernameError = false
    @State var isPasswordError = false
    
    var body: some View {
        
        ZStack{
            NavigationView{
                VStack{
                    TextField("Name", text: $userName)
                        .padding([.top, .leading, .trailing])
                    UnderLine()
                    SecureField("Password", text: $userPwd)
                        .padding([.top, .leading, .trailing])
                    UnderLine()
                    HStack{
                        Spacer()
                        NavigationLink(
                            destination: RegisterView(),
                            isActive: $isCreateAccount,
                            label: {
                                Button(action: {
                                    isCreateAccount.toggle()
                                }) {
                                    Text("Create Account")
                                        .foregroundColor(Color.gray)
                                        .font(.subheadline)
                                        .padding(.top)
                                }
                            })
                    }
                    .padding(.bottom, UIScreen.main.bounds.height/12)
                    NavigationLink(
                        destination: LoginSuccessView(),
                        isActive: $isLoginSuccess,
                        label: {
                            Button(action: {
                                if self.userName != self.userInfo.mockUserName{
                                    print("用户名错误")
                                    self.isUsernameError.toggle()
                                }else{
                                    if self.userPwd != userInfo.mockPassword {
                                        print("密码错误")
                                        self.isPasswordError.toggle()
                                    }else{
                                        print("验证完成")
                                        self.isLoginSuccess.toggle()
                                    }
                                }
                            }) {
                                ZStack{
                                    Rectangle()
                                        .frame(width: UIScreen.main.bounds.width/1.3, height: UIScreen.main.bounds.height/15)
                                        .foregroundColor(self.userPwd.count>=7 && !self.userName.isEmpty ? .green : .gray)
                                        .cornerRadius(10)
                                    Text("Login")
                                        .foregroundColor(.white)
                                }
                            }
                            .alert(isPresented: $isUsernameError) {
                                        Alert(title: Text("Username ERROR!"), message: Text("Username error,please cheak it!"), dismissButton: .default(Text("OK")))
                                    }
                            .alert(isPresented: $isPasswordError) {
                                        Alert(title: Text("Password ERROR!"), message: Text("Password error,please cheak it!"), dismissButton: .default(Text("OK")))
                                    }
                        })
                        .disabled(self.userPwd.count<=7 && !self.userName.isEmpty)
                    Spacer()
                }.padding()//输入账号密码
            }.navigationBarHidden(true)
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}

