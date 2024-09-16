//
//  LoginContentView.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import SwiftUI

struct LoginContentView: View {
    //MARK: - Property -
    //用户名
    @State var username = ""
    //密码
    @State var password = ""
    //登录按钮是否可点击
    @State var loginEnable:Int? = 0
    //登录提示
    @State var msg = " "
    //viewmodel
    let viewModel = LoginViewModel()
    //MARK: - View -
    var body: some View {
        let inputEdge = EdgeInsets.init(top: 0, leading: 30, bottom: 0, trailing: 30)
        let lineEdge = EdgeInsets.init(top: 0, leading: 20, bottom: 20, trailing: 20)
        //导航
        NavigationView{
            //竖向
            VStack() {
                //用户名
                TextField("Name", text: $username).font(Font.system(size: 15)).padding(inputEdge)
                //分割线
                Divider().padding(lineEdge)
                //密码
                SecureField("Password", text: $password)
                    .font(Font.system(size: 15))
                    .padding(inputEdge)
                // 分割线
                Divider().padding(lineEdge)
                //创建用户
                HStack() {
                    Spacer()
                    //跳转至创建用户
                    NavigationLink(
                        destination: RegisterView(),
                        label: {
                            Text("Creat Account")
                                .foregroundColor(.gray.opacity(0.5))
                                .font(.system(size: 15))
                        }).padding(EdgeInsets.init(top: 0, leading: 20, bottom: 0, trailing: 20))
                }.padding(.bottom, 40)
                //登录跳转至主页
                NavigationLink(
                    destination: HomeView(),
                    tag: /*@START_MENU_TOKEN@*/1/*@END_MENU_TOKEN@*/,
                    selection: $loginEnable,
                    label: {
                        Button(action: {
                            //登录
                            loginAction()
                        }, label: {
                            Text("Login")
                                .foregroundColor(.white)
                                .frame(width: ScreenWidth-40, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                        }).frame(width: ScreenWidth-40, height: 40, alignment: .center)
                    }).background(loginBtnBackgroundColor())
                    .cornerRadius(8)
                    .disabled(!checkLoginInput())
                //登录错误提示
                Text("\(self.msg)")
                    .frame(width: ScreenWidth, height: 20, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                    .foregroundColor(.red)
                    .font(.system(size: 15))
                    .padding(.top, 50)
            }.navigationBarHidden(true)
            .statusBar(hidden: true)
        }
    }
    
    //MARK: - Method -
    //MARK:校验用户名,密码输入
    func checkLoginInput() -> Bool{
        //用户名不为空,密码大于 8 个字符
        if(self.username != "" && self.password.count >= PasswordMinLength) {
            return true
        } else {
            return false
        }
    }
    
    //MARK: 登录按钮背景色
    func loginBtnBackgroundColor() -> Color {
      return  self.username.count > 0 && self.password.count >= PasswordMinLength ? Color.green : Color.gray.opacity(3.0)
    }
    
    //MARK:登录方法
    func loginAction() {
        //请求
        viewModel.login(username: self.username, password: self.password) { success, msg in
            if(success) {
                self.loginEnable = 1
                self.username = ""
                self.password = ""
                self.msg = ""
            } else {
                self.loginEnable = 0
                self.msg = msg
            }
        }
    }
}

struct LoginContentView_Previews: PreviewProvider {
    static var previews: some View {
        LoginContentView()
    }
}
