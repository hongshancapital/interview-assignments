//
//  RegisterView.swift
//  SwiftUIHomeWork
//
//  Created by RemiliaScarlet on 2021/3/15.
//

import SwiftUI

struct RegisterView: View {
    
    @EnvironmentObject var userInfo:MockUserInfo
    
    @State var registerUserName:String = ""
    @State var registerUserPwd:String = ""
    @State var registerUserPwdAgain:String = ""
    @State var isRegisterSuccess = false
    @State var registerSuccessAlert = false

    var body: some View {
        ZStack{
            //NavigationView{
                VStack{
                    Spacer()
                        .frame(height:UIScreen.main.bounds.height/5)
                    TextField("Name", text: $registerUserName)
                        .padding([.top, .leading, .trailing])
                    UnderLine()
                    
                    SecureField("Password", text: $registerUserPwd)
                        .padding([.top, .leading, .trailing])
                    UnderLine()
                    
                    SecureField("Repeat password", text: $registerUserPwdAgain)
                        .padding([.top, .leading, .trailing])
                    UnderLine()
                    //三个TextField
                    NavigationLink(
                        destination: LoginView(),
                        isActive: $isRegisterSuccess,
                        label: {
                            Button(action: {
                                self.userInfo.mockUserName = self.registerUserName
                                self.userInfo.mockPassword = self.registerUserPwd
                                self.registerSuccessAlert.toggle()
                                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 1) {
                                    self.isRegisterSuccess.toggle()
                                    print("1 秒后输出")
                                }
                                
                            }) {
                                ZStack{
                                    Rectangle()
                                        .frame(width: UIScreen.main.bounds.width/1.3, height: UIScreen.main.bounds.height/15)
                                        .foregroundColor(!self.registerUserName.isEmpty && registerUserPwd.count>=7 && registerUserPwdAgain.count>=7 && registerUserPwd == registerUserPwdAgain ? .green : .gray)
                                        .cornerRadius(10)
                                    Text("Create Account")
                                        .foregroundColor(.white)
                                }
                            }
                            .alert(isPresented: $registerSuccessAlert) {
                                        Alert(title: Text("Register Success"), message: Text("Register success,you can login now!"))
                                    }
                        }).disabled( !(!self.registerUserName.isEmpty && registerUserPwd.count>=7 && registerUserPwdAgain.count>=7 && registerUserPwd == registerUserPwdAgain) )
                        
                        //navLink到登录页
                        .padding(.top, UIScreen.main.bounds.height/12)
                    Spacer()
                }.padding()//输入账号密码
            //}
        }
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}
