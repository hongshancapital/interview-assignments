//
//  RegisterView.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import SwiftUI

struct RegisterView: View {
    //MARK: - Property -
    //用户名
    @State var username = ""
    //密码
    @State var password = ""
    //确认密码
    @State var confirmPassword = ""
    //创建提示
    @State var msg = " "
    //viewmodel
    let viewModel = RegisterViewModel()
    //用于退出该界面
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    //MARK: - View -
    var body: some View {
        let inputEdge = EdgeInsets.init(top: 0, leading: 30, bottom: 0, trailing: 30)
        let lineEdge = EdgeInsets.init(top: 0, leading: 20, bottom: 20, trailing: 20)
        VStack() {
            //用户名
            TextField("Name", text: $username).font(Font.system(size: 15)).padding(inputEdge)
            //分割线
            Divider().padding(lineEdge)
            //密码
            SecureField("Password", text: $password)
                .font(Font.system(size: 15))
                .padding(inputEdge)
            //分割线
            Divider().padding(lineEdge)
            //确认密码
            SecureField("Repeat Password", text: $confirmPassword)
                .font(Font.system(size: 15))
                .padding(inputEdge)
            //分割线
            Divider().padding(EdgeInsets.init(top: 0, leading: 20, bottom: 30, trailing: 20))
            //创建账号按钮
            Button(action: {
                creatUserAction()
            }, label: {
                Text("Creat Account")
                    .foregroundColor(.white)
            }).frame(width: ScreenWidth-40, height: 40, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            .background(loginBtnBackgroundColor())
            .cornerRadius(8)
            .disabled(!checkRegisterInput())
            Text("\(self.msg)")
                .frame(width: ScreenWidth, height: 20, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .foregroundColor(.red)
                .font(.system(size: 15))
                .padding(.top, 50)
        }.padding(EdgeInsets.init(top: 0, leading: 50, bottom: 0, trailing: 50))
        .navigationBarHidden(true)
        .statusBar(hidden: true)
    }
    //MARK: - Method -
    //MARK: 创建账号按钮背景色
    func loginBtnBackgroundColor() -> Color {
        if(self.username.count > 0 && self.password.count >= PasswordMinLength && self.confirmPassword.count >= PasswordMinLength) {
            return Color.green
        }
        return Color.gray.opacity(3.0)
    }
    //MARK:校验用户名,密码输入
    func checkRegisterInput() -> Bool {
        //用户名不为空,密码大于 8 个字符
        if(self.username != "" && self.password.count >= PasswordMinLength && self.confirmPassword.count >= PasswordMinLength) {
            return true
        } else {
            return false
        }
    }
    
    //MARK: 点击了创建账号
    func creatUserAction() {
        if (self.password != self.confirmPassword) {
            self.msg = "Repeat Password error!"
            return
        }
        viewModel.registerAccount(username: username, password: password) { success, msg in
            if success {
                //跳转回登录页
                self.presentationMode.wrappedValue.dismiss()
            }
            self.msg = msg
        }
    }

}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}
